package cn.xiaobaihome.xiaobaihelper.api

import cn.xiaobaihome.xiaobaihelper.mvvm.model.ApkInfo
import cn.xiaobaihome.xiaobaihelper.mvvm.model.DefaultResult
import cn.xiaobaihome.xiaobaihelper.mvvm.model.NewItem
import cn.xiaobaihome.xiaobaihelper.mvvm.model.Status
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import retrofit2.Call
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

}