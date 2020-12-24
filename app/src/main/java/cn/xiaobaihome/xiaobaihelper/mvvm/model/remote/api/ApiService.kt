package cn.xiaobaihome.xiaobaihelper.mvvm.model.remote.api

import cn.xiaobaihome.xiaobaihelper.mvvm.model.data.VersionInfo
import retrofit2.http.GET

interface ApiService {

    @GET("https://www.xiaobaihome.cn/app/version/getVersion")
    suspend fun getVersion(): VersionInfo
}