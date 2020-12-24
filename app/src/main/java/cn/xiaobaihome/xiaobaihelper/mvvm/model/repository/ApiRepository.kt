package cn.xiaobaihome.xiaobaihelper.mvvm.model.repository

import cn.xiaobaihome.xiaobaihelper.mvvm.model.data.VersionInfo
import cn.xiaobaihome.xiaobaihelper.mvvm.model.remote.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApiRepository(private val remote: ApiService) {
    suspend fun getVersion(): VersionInfo {
        return withContext(Dispatchers.IO){
            remote.getVersion()
        }
    }
}