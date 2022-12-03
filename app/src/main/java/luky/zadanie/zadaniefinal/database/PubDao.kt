package luky.zadanie.zadaniefinal.database

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPubsDao(pubs: List<Pub>)

    @Query("DELETE FROM pub;")
    suspend fun deletePubsDao()

    @Query("SELECT * from pub;")
    fun getAllPubs(): Flow<List<Pub>>

    @Query("SELECT * from pub WHERE pubId = :idPub")
    fun getPub(idPub: String): Flow<Pub>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPubDetailDao(pubDetail: PubDetail)

    @Query("DELETE FROM detail;")
    suspend fun deletePubDetailDao()

    @Query("SELECT * from detail;")
    fun getDetailPub(): Flow<PubDetail>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPubNearDao(pubNear: List<PubNear>)

    @Query("DELETE FROM near_pub;")
    suspend fun deletePubNearDao()

    @Query("SELECT * from near_pub;")
    fun getAllNearPubs(): Flow<List<PubNear>>




}