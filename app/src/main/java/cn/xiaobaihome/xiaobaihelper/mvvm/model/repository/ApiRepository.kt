package cn.xiaobaihome.xiaobaihelper.mvvm.model.repository

import cn.xiaobaihome.xiaobaihelper.mvvm.model.data.VersionInfo
import cn.xiaobaihome.xiaobaihelper.mvvm.model.remote.api.ApiService
import io.reactivex.Single

class ApiRepository(private val remote: ApiService) {
    fun getVersion(): Single<VersionInfo> = remote.getVersion()
}