package cn.xiaobaihome.xiaobaihelper.mvvm.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.xiaobaihome.xiaobaihelper.mvvm.model.DefaultResult
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * viewModel进行数据，页面状态的存储和网络请求
 * 一般界面有以下几种请求：
 * 1.数据铺到整个界面上，失败显示失败布局，点击重新请求。例如：首页，详情页等   PageState
 * 2.数据铺到列表上，失败重新请求。列表又分为加载更多和首次请求/刷新          ListState
 * 3.数据铺到某个控件上，成功铺数据，失败弹出提示。例如：收藏                ViewState
 * 4.请求数据后成功显示弹窗，失败啥也不干。例如：检查更新                   State
 * 所有请求可能都需要一个loading                                       LoadingState
 * */
open class BaseViewModel : ViewModel() {

    val loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val pageState: MutableStateFlow<PageState> = MutableStateFlow(PageState.STATE_LOADING)
    val alertState: MutableStateFlow<String> = MutableStateFlow("")

    fun showLoading() {
        loadingState.value = true
    }

    fun hideLoading() {
        loadingState.value = false
    }

    fun <T> launch2(
        value: T?,
        autoShowLoading: Boolean = true,
        action: suspend (value: T?) -> Unit
    ) {
        if(autoShowLoading) showLoading()
        viewModelScope.launch {
            flow {
                emit(value)
            }
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    if(autoShowLoading) hideLoading()
                    action(value)
                }
        }
    }

    fun <T> launch(
        value: DefaultResult<T?>?,
        autoShowLoading: Boolean = true,
        action: suspend (value: T?) -> Unit
    ) {
        if(autoShowLoading) showLoading()
        viewModelScope.launch {
            flow {
                emit(value)
            }
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    if(autoShowLoading) hideLoading()
                    action(value?.data)
                }
        }
    }
}