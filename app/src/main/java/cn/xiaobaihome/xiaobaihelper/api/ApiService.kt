package cn.xiaobaihome.xiaobaihelper.api

import cn.xiaobaihome.xiaobaihelper.mvvm.model.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @GET("/version/getVersion")
    suspend fun getVersion(): DefaultResult<ApkInfo?>?

    @GET("/news/getNewsList")
    suspend fun getNews(
        @Query("type") type: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): DefaultResult<List<NewItem>?>?

    @FormUrlEncoded
    @POST("http://192.168.1.2/cgi-bin/luci/")
    suspend fun login(
        @Field("luci_username") username: String,
        @Field("luci_password") password: String
    ): String

    @GET("http://192.168.1.2/cgi-bin/luci/")
    suspend fun getStatus(
        @Query("status") status: String,
        @Query("_") timestamp: String
    ): Status

    companion object {

        private const val BASE_URL = "https://api.xiaobaihome.cn/"

        fun create(): ApiService {
            val okHttpClient = OkHttpManager.instance.okHttpClient
            val gson = GsonManager.instance.gson
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
                .create(ApiService::class.java)
        }
    }

}