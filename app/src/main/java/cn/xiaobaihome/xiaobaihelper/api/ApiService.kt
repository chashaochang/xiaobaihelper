package cn.xiaobaihome.xiaobaihelper.api

import cn.xiaobaihome.xiaobaihelper.mvvm.model.ApkInfo
import cn.xiaobaihome.xiaobaihelper.mvvm.model.DefaultResult
import cn.xiaobaihome.xiaobaihelper.mvvm.model.NewItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/version/getVersion")
    suspend fun getVersion(): DefaultResult<ApkInfo?>?

    @GET("/news/getNewsList")
    suspend fun getNews(@Query("type") type: String, @Query("page") page: Int, @Query("size") size: Int): DefaultResult<List<NewItem>?>?
}