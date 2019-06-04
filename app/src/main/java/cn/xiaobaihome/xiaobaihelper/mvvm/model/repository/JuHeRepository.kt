package cn.xiaobaihome.xiaobaihelper.mvvm.model.repository

import cn.xiaobaihome.xiaobaihelper.mvvm.model.data.JuHeResp
import cn.xiaobaihome.xiaobaihelper.mvvm.model.remote.api.JuHeService
import io.reactivex.Single

class JuHeRepository(private val remote: JuHeService) {

    fun getNews(type: String, key: String): Single<JuHeResp> = remote.getNews(type, key)
}