package cn.xiaobaihome.xiaobaihelper

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
import org.jsoup.Jsoup
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val apiService: ApiService) : BaseViewModel() {

    fun login(username: String, password: String, respResult: RespResult<String>) {
        viewModelScope.launch {
            try {
                val value = apiService.login(username, password)
                flow {
                    emit(value)
                }.flowOn(Dispatchers.IO).collectLatest {
                    respResult.onSuccess(it)
                    val doc = Jsoup.parse(it)

                }
            }catch (e:Exception){
                respResult.onError(e.toString())
            }

        }
    }
}