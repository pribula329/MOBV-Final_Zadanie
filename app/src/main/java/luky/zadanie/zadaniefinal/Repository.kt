package luky.zadanie.zadaniefinal

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import luky.zadanie.zadaniefinal.database.Pub
import luky.zadanie.zadaniefinal.database.PubDetail
import luky.zadanie.zadaniefinal.database.PubNear
import luky.zadanie.zadaniefinal.database.PubRoomDatabase
import luky.zadanie.zadaniefinal.helper.distanceToNearPub
import luky.zadanie.zadaniefinal.helper.setName
import luky.zadanie.zadaniefinal.network.ApiService
import luky.zadanie.zadaniefinal.network.PubDetailData
import luky.zadanie.zadaniefinal.network.UserRequestData
import luky.zadanie.zadaniefinal.network.UserResponseData
import java.io.IOException
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class Repository private constructor(
    private val apiService: ApiService,
    private val pubRoomDatabase: PubRoomDatabase
){


    suspend fun registerUserRepository(
        name: String,
        password: String,
        onError: (error: String) -> Unit,
        onStatus: (success: UserResponseData?) -> Unit
    ) {
        try {
            val passwordHash = hashUserData(password)
            val response = apiService.userCreateService(UserRequestData(name = name, password = passwordHash))
            if (response.isSuccessful) {
                response.body()?.let { user ->
                    if (user.uid == "-1"){
                        onStatus(null)
                        onError("User with your name already exist")

                    }else {
                        onStatus(user)
                    }
                }
            } else {
                onError("Failed to register.")
                onStatus(null)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            onError("Failed to register.")
            onStatus(null)
        }
    }

    suspend fun loginUserRepository(
        name: String,
        password: String,
        onError: (error: String) -> Unit,
        onStatus: (success: UserResponseData?) -> Unit
    ) {
        try {
            val passwordHash = hashUserData(password)
            val response = apiService.userLoginService(UserRequestData(name = name, password = passwordHash))
            if (response.isSuccessful) {
                response.body()?.let { user ->
                    if (user.uid == "-1"){
                        onStatus(null)
                        onError("Please check your input field. Name or password is incorrect")
                    }else {

                        onStatus(user)
                    }
                }
            } else {
                onError("Failed to login.")
                onStatus(null)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            onError("Failed to login.")
            onStatus(null)
        }
    }

    suspend fun apiPubListRepository(
        onError: (error: String) -> Unit
    ) {
        try {
            val response = apiService.pubListService()
            if (response.isSuccessful) {
                response.body()?.let { pubs ->

                    val newPubs = pubs.map {
                        Pub(
                            it.pubId,
                            it.pubName,
                            it.pubType,
                            it.lat,
                            it.lon,
                            it.usersCount
                        )
                    }
                    pubRoomDatabase.pubDao().deletePubsDao()
                    pubRoomDatabase.pubDao().insertPubsDao(newPubs)
                } ?: onError("Failed to load pubs")
            } else {
                onError("Failed to load pubs")
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            onError("Failed to load pubs, try it again or check internet connection")

        }
    }

    fun getPubsRepository(): LiveData<List<Pub>> {
        return Transformations.map(pubRoomDatabase.pubDao().getAllPubs().asLiveData()) {
            it
        }
    }


    suspend fun apiPubDetailRepository(
        id: String,
        onError: (error: String) -> Unit
    ){
        pubRoomDatabase.pubDao().deletePubDetailDao()
        try {
            val response = apiService.pubDetailService("[out:json];node($id);out body;>;out skel;")
            if (response.isSuccessful){
                response.body()?.let { pubDetail ->
                    val newPubDetail = PubDetail(pubDetail.elements[0].id,
                        pubDetail.elements[0].tags.email,
                        pubDetail.elements[0].tags.phone,
                        pubDetail.elements[0].tags.website)


                    pubRoomDatabase.pubDao().insertPubDetailDao(newPubDetail)
                }
            }
            else{
                onError("Failed to load more pub detail")
            }
        }
        catch (ex: IOException){
            ex.printStackTrace()
            onError("Failed to load more pub detail, try it again or check internet connection")
        }

    }

    fun getDetailPubRepository(): LiveData<PubDetail> {
        return Transformations.map(pubRoomDatabase.pubDao().getDetailPub().asLiveData()) {
            it
        }
    }


    suspend fun apiPubNearRepository(
        myLat: Double,
        myLon: Double,
        onError: (error: String) -> Unit
    ){
        pubRoomDatabase.pubDao().deletePubNearDao()
        try {
            val response =
                apiService.pubNearService("[out:json];node(around:250,$myLat,$myLon);(node(around:250)[\"amenity\"~\"^pub$|^bar$|^restaurant$|^cafe$|^fast_food$|^stripclub$|^nightclub$\"];);out body;>;out skel;")
            if (response.isSuccessful) {
                response.body()?.let { nearPubs ->
                    var nearPubsList: List<PubNear> = nearPubs.elements.map {
                        PubNear(
                            it.id,
                            it.lat,
                            it.lon,
                            it.tagsNear.pubNearType,
                            setName(it.tagsNear.pubNearName),
                            distanceToNearPub(myLat, myLon,it.lat,it.lon)
                        )
                    }


                    nearPubsList = nearPubsList.filter { it.nearName.isNotBlank() }.sortedBy { it.distance }
                    pubRoomDatabase.pubDao().insertPubNearDao(nearPubsList)
                }



            }
            else{
                onError("Failed to load more pub")
            }
        }
        catch (ex: IOException){
            ex.printStackTrace()
            onError("Failed to load more pub, try it again or check internet connection")
        }

    }

    fun getNearPubRepository(): LiveData<List<PubNear>> {
        return Transformations.map(pubRoomDatabase.pubDao().getAllNearPubs().asLiveData()) {
            it
        }
    }


    private fun hashUserData(password: String): String {
        var digest = ""
        try {
            val crypt = MessageDigest.getInstance("MD5");
            crypt.update(password.toByteArray());
            digest = BigInteger(1, crypt.digest()).toString(16)


        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return digest
    }


    companion object{
        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(apiService: ApiService, pubRoomDatabase: PubRoomDatabase): Repository =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: Repository(apiService,pubRoomDatabase).also { INSTANCE = it }
            }

    }
}