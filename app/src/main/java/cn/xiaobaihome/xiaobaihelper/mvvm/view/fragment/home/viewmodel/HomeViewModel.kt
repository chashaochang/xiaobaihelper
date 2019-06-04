package cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.home.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import cn.xiaobaihome.xiaobaihelper.helper.extens.async
import cn.xiaobaihome.xiaobaihelper.mvvm.model.repository.JuHeRepository
import cn.xiaobaihome.xiaobaihelper.mvvm.viewmodel.PagedViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class HomeViewModel
constructor(private val repo: JuHeRepository) :PagedViewModel(){

    fun loadData(isRefresh: Boolean) = repo.getNews("top", "6240d53186ae1b9e03bf737937258408")
            .async(1000)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}