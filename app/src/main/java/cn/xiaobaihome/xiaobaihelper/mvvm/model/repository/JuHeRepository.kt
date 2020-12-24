package cn.xiaobaihome.xiaobaihelper.mvvm.model.repository

import cn.xiaobaihome.xiaobaihelper.mvvm.model.data.JuHeResp
import cn.xiaobaihome.xiaobaihelper.mvvm.model.remote.api.JuHeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JuHeRepository(private val remote: JuHeService) {

    suspend fun getNews(type: String, key: String): JuHeResp = withContext(Dispatchers.IO){
        remote.getNews(type, key)
    }
}