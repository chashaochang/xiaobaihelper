package cn.xiaobaihome.xiaobaihelper.miniprogram

import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MiniProgramViewModel : BaseViewModel() {

    val url: MutableStateFlow<String> by lazy { MutableStateFlow("") }
}