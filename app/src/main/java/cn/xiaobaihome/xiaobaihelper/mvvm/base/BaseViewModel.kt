package cn.xiaobaihome.xiaobaihelper.mvvm.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.xiaobaihome.xiaobaihelper.mvvm.model.DefaultResult
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    val handler = CoroutineExceptionHandler { context, exception ->
        println("Caught $exception")
    }

    fun <T> launch(value: DefaultResult<T?>?, action: suspend (value: T?) -> Unit) {
        viewModelScope.launch(handler) {
            try {
                flow {
                    emit(value)
                }
                    .flowOn(Dispatchers.IO)
                    .catch { e->
                        Log.i("BaseViewModel1", e.toString())
                    }
                    .collectLatest {
                    action(value?.data)
                }
            } catch (e:Exception){
                Log.i("BaseViewModel", e.toString())
            }

        }
    }
}