package cn.xiaobaihome.xiaobaihelper.api

import cn.xiaobaihome.xiaobaihelper.mvvm.model.OpenWrtStatus
import retrofit2.Retrofit
import retrofit2.http.*

interface OpenWrtApiService {

    @Headers("type:openwrt")
    @POST("/cgi-bin/luci/")
    @FormUrlEncoded
    suspend fun login(
        @Field("luci_username") username: String,
        @Field("luci_password") password: String
    ): Any

    @Headers("type:openwrt")
    @POST("/cgi-bin/luci/")
    suspend fun getStatus(
        @Query("status") status: Int = 1,
        @Query("_") timestamp: Long = System.currentTimeMillis()
    ): OpenWrtStatus

    @Headers("type:openwrt")
    @POST("/cgi-bin/luci/")
    suspend fun getHtml(): String

    companion object {

        var BASE_URL = ""
        var isInit = false
        lateinit var instance: OpenWrtApiService

        fun create(): OpenWrtApiService {
            if (isInit) return instance
            val okHttpClient = OkHttpManager.instance.okHttpClient
            val gson = GsonManager.instance.gson
            instance = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
                .create(OpenWrtApiService::class.java)
            isInit = true
            return instance
        }
    }
}