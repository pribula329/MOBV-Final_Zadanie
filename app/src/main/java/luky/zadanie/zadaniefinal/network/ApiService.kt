package luky.zadanie.zadaniefinal.network

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @POST("user/create.php")
    suspend fun userCreateService(@Body user: UserRequestData): Response<UserResponseData>

    @POST("user/login.php")
    suspend fun userLoginService(@Body user: UserRequestData): Response<UserResponseData>

    @POST("user/refresh.php")
    fun userRefreshService(@Body user: UserRefreshData) : Call<UserResponseData>

    @GET("bar/list.php")
    @Headers("mobv-auth: accept")
    suspend fun pubListService() : Response<List<PubListData>>

    @GET("https://overpass-api.de/api/interpreter?")
    suspend fun pubDetailService(@Query("data") data: String): Response<PubDetailResponse>

    companion object{
        const val BASE_URL = "https://zadanie.mpage.sk/"

        fun create(context: Context): ApiService {
            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .authenticator(TokenAuthentificator(context))
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}