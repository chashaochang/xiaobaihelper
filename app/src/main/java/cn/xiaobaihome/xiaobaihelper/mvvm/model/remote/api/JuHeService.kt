package cn.xiaobaihome.xiaobaihelper.mvvm.model.remote.api

import cn.xiaobaihome.xiaobaihelper.mvvm.model.data.JuHeResp
import retrofit2.http.GET
import retrofit2.http.Query

interface JuHeService {

    @GET("http://v.juhe.cn/toutiao/index")
    suspend fun getNews(@Query("type") type: String, @Query("key") key: String): JuHeResp
}