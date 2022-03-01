package cn.xiaobaihome.xiaobaihelper.mvvm.view.home

import androidx.lifecycle.MutableLiveData
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseViewModel
import cn.xiaobaihome.xiaobaihelper.api.ApiService
import cn.xiaobaihome.xiaobaihelper.api.ApiException
import cn.xiaobaihome.xiaobaihelper.mvvm.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiService: ApiService) : BaseViewModel() {

    var news: MutableLiveData<List<NewItemParse>> = MutableLiveData()

    init {
        news.value = ArrayList()
    }

    suspend fun loadData() {
        launch(apiService.getNews("top", 1, 30)){
            if (!it.isNullOrEmpty()) {
                val list = ArrayList<NewItemParse>()
                for (i in it) {
                    list.add(NewItemParse(i))
                }
                news.postValue(list)
            }
        }
    }

    suspend fun getVersion(respResult: RespResult<ApkInfo>) {
        try {
            launch(apiService.getVersion()) {
                if (it != null) {
                    respResult.onSuccess(it)
                }
            }
        } catch (e: ApiException) {
            respResult.onError(e.message)
        }

    }

}