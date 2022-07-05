package cn.xiaobaihome.xiaobaihelper.mvvm.view.openwrt

import androidx.lifecycle.viewModelScope
import cn.xiaobaihome.xiaobaihelper.api.ApiService
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseViewModel
import cn.xiaobaihome.xiaobaihelper.mvvm.model.RespResult
import cn.xiaobaihome.xiaobaihelper.mvvm.model.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpViewModel @Inject constructor(private val apiService: ApiService) :BaseViewModel() {


    fun getStatus(status: String, timestamp: String, respResult: RespResult<Status>) {
        viewModelScope.launch {
            try {
                val value = apiService.getStatus(status, timestamp)
                flow {
                    emit(value)
                }.flowOn(Dispatchers.IO).collectLatest {
                    respResult.onSuccess(it)
                }
            }catch (e:Exception){
                respResult.onError(e.toString())
            }

        }
    }
}