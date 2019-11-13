package cn.xiaobaihome.xiaobaihelper.mvvm.model.remote.api

import cn.xiaobaihome.xiaobaihelper.mvvm.model.data.VersionInfo
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("https://www.xiaobaihome.cn/app/version/getVersion")
    fun getVersion(): Single<VersionInfo>
}