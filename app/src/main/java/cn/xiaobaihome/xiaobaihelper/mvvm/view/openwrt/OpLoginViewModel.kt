package cn.xiaobaihome.xiaobaihelper.mvvm.view.openwrt

import cn.xiaobaihome.xiaobaihelper.api.ApiService
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OpLoginViewModel @Inject constructor(val apiService: ApiService) : BaseViewModel() {

}