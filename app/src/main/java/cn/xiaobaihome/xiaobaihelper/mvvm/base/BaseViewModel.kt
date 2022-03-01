package cn.xiaobaihome.xiaobaihelper.mvvm.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.xiaobaihome.xiaobaihelper.mvvm.model.DefaultResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    fun <T> launch(value: DefaultResult<T?>?, action: suspend (value: T?) -> Unit) {
        viewModelScope.launch {
            flow {
                emit(value)
            }.flowOn(Dispatchers.IO).collectLatest {
                action(value?.data)
            }
        }
    }
}