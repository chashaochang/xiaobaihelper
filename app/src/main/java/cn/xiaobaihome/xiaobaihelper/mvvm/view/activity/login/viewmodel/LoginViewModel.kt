package cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    var canSee: MutableLiveData<Boolean> = MutableLiveData()
}