package cn.xiaobaihome.xiaobaihelper.mvvm.view.home

import android.util.Log
import cn.xiaobaihome.xiaobaihelper.api.*
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseViewModel
import cn.xiaobaihome.xiaobaihelper.bean.IkuaiBaseReq
import cn.xiaobaihome.xiaobaihelper.helper.CacheUtil
import cn.xiaobaihome.xiaobaihelper.mvvm.base.PageState
import cn.xiaobaihome.xiaobaihelper.mvvm.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.jsoup.Jsoup
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiService: ApiService) : BaseViewModel() {

    var news: MutableStateFlow<List<NewItemParse>> = MutableStateFlow(mutableListOf())
    var apkInfo: MutableStateFlow<ApkInfo> = MutableStateFlow(ApkInfo())
    var minerStatus: MutableStateFlow<MinerStatus> = MutableStateFlow(MinerStatus())
    var ikuaiStatus: MutableStateFlow<IKuaiStatus> = MutableStateFlow(IKuaiStatus())
    var openWrtStatus: MutableStateFlow<OpenWrtStatus> = MutableStateFlow(OpenWrtStatus())
    val openWrtInfo: MutableStateFlow<OpenWrtInfo> = MutableStateFlow(OpenWrtInfo())

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

    suspend fun getIKuaiStatus() {
        val ikuaiService = IKuaiApiService.create()
        try {
            launch2(
                ikuaiService.getStatus(
                    IkuaiBaseReq(
                        func_name = "homepage",
                        action = "show",
                        param = IkuaiBaseReq.Param(
                            TYPE = "sysstat,ac_status"
                        )
                    )
                )
            ) {
                if (it != null) {
                    if (it.data != null) {
                        ikuaiStatus.value = it.data
                    } else if (it.result == 10014) {
                        CacheUtil.set(CacheUtil.IKUAI_COOKIE, "")
                    } else {
                        alertState.value = it.errMsg
                    }
                }
            }
        } catch (e: Exception) {
//            if (e is ApiException) {
//                if (e.code != 4004)
//                    alertState.value = e.message
//            } else {
//                alertState.value = e.message.toString()
//            }
        }
    }

    suspend fun getOpenWrtInfoHtml() {
        val openWrtService = OpenWrtApiService.create()
        try {
            launch2(openWrtService.getHtml()) {
                it?.let {
                    val document = Jsoup.parse(it)
                    val fieldSets = document.getElementsByClass("cbi-section")
                    for (i in fieldSets) {
                        val tableItems = i.getElementsByTag("tr")
                        for (j in tableItems) {
                            if (j.childrenSize() == 2){
                                val key = j.child(0).text()
                                val value = j.child(1).text()
                                when (key) {
                                    "主机名" -> openWrtInfo.value.name = value
                                    "主机型号" -> openWrtInfo.value.device = value
                                    "固件版本" -> openWrtInfo.value.firmwareVersion = value
                                    "内核版本" -> openWrtInfo.value.kernelVersion = value
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
//            if (e is ApiException) {
//                if (e.code != 4004)
//                    alertState.value = e.message
//            } else {
//                alertState.value = e.message.toString()
//            }
        }
    }

    suspend fun getOpenWrtStatus() {
        val openWrtService = OpenWrtApiService.create()
        try {
            launch2(openWrtService.getStatus()) {
                if (it != null) {
                    openWrtStatus.value = it
                }
            }
        } catch (e: Exception) {
//            if (e is ApiException) {
//                if (e.code != 4004)
//                    alertState.value = e.message
//            } else {
//                alertState.value = e.message.toString()
//            }
        }
    }

    suspend fun getMinerStatus() {
        val minerService = MinerApiService.create()
        try {
            launch2(minerService.getMinerStatus()) {
                if (it != null) {
                    minerStatus.value = it
                }
            }
        } catch (e: Exception) {
//            if (e is ApiException) {
//                if (e.code != 4004)
//                    alertState.value = e.message
//            } else {
//                alertState.value = e.message.toString()
//            }
        }
    }

}