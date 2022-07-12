package cn.xiaobaihome.xiaobaihelper.mvvm.view.home

import android.util.Log
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseViewModel
import cn.xiaobaihome.xiaobaihelper.api.ApiService
import cn.xiaobaihome.xiaobaihelper.api.ApiException
import cn.xiaobaihome.xiaobaihelper.api.MinerApiService
import cn.xiaobaihome.xiaobaihelper.mvvm.base.PageState
import cn.xiaobaihome.xiaobaihelper.mvvm.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiService: ApiService) : BaseViewModel() {

    var news: MutableStateFlow<List<NewItemParse>> = MutableStateFlow(mutableListOf())
    var apkInfo: MutableStateFlow<ApkInfo> = MutableStateFlow(ApkInfo())
    var minerStatus: MutableStateFlow<MinerStatus> = MutableStateFlow(MinerStatus())

    suspend fun loadData() {
//        launch(apiService.getNews("top", 1, 30)) {
//            if (!it.isNullOrEmpty()) {
//                val list = ArrayList<NewItemParse>()
//                for (i in it) {
//                    list.add(NewItemParse(i))
//                }
//                news.value = list
//            }
//        }
        flow {
            showLoading()
            kotlinx.coroutines.delay(2000)
            emit("测试")
        }.flowOn(Dispatchers.IO)
            .collect {
                hideLoading()
                alertState.value = it
            }
        pageState.value = PageState.STATE_EMPTY
    }

    suspend fun getVersion() {
        try {
            launch(apiService.getVersion(), autoShowLoading = false) {
                if (it != null) {
                    apkInfo.value = it
                }
            }
        } catch (e: Exception) {
            if (e is ApiException) {
                if (e.code != 4004)
                    alertState.value = e.message
            } else {
                alertState.value = e.message.toString()
            }
        }
    }

    suspend fun getMinerStatus(){
        val minerService = MinerApiService.create()
        try {
            launch2(minerService.getMinerStatus()) {
                if (it != null) {
                    Log.i("TAG", "getMinerStatus: "+it.version)
                    minerStatus.value = it
                }
            }
        } catch (e: Exception) {
            if (e is ApiException) {
                if (e.code != 4004)
                    alertState.value = e.message
            } else {
                alertState.value = e.message.toString()
            }
        }
    }

}