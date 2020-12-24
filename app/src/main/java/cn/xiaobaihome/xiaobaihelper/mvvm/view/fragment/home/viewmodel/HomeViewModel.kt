package cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.xiaobaihome.xiaobaihelper.mvvm.model.data.JuHeResp
import cn.xiaobaihome.xiaobaihelper.mvvm.model.data.RespResult
import cn.xiaobaihome.xiaobaihelper.mvvm.model.data.VersionInfo
import cn.xiaobaihome.xiaobaihelper.mvvm.model.repository.ApiRepository
import cn.xiaobaihome.xiaobaihelper.mvvm.model.repository.JuHeRepository
import kotlinx.coroutines.launch

class HomeViewModel
constructor(private val repo: JuHeRepository, private var apiRepo: ApiRepository) :ViewModel(){

    fun loadData(respResult: RespResult<JuHeResp>) {
        viewModelScope.launch {
            try {
                respResult.onSuccess(repo.getNews("top", "6240d53186ae1b9e03bf737937258408"))
            } catch (e: Exception) {
                respResult.onError(e.toString())
            }

        }
    }

    fun getVersion(respResult: RespResult<VersionInfo>) {
        viewModelScope.launch {
            try {
                respResult.onSuccess(apiRepo.getVersion())
            } catch (e: Exception) {
                respResult.onError(e.toString())
            }

        }
    }

}