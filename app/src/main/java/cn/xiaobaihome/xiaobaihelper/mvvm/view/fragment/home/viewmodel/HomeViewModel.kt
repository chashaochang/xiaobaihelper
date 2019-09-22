package cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.home.viewmodel

import cn.xiaobaihome.xiaobaihelper.mvvm.model.repository.ApiRepository
import cn.xiaobaihome.xiaobaihelper.mvvm.model.repository.JuHeRepository
import cn.xiaobaihome.xiaobaihelper.mvvm.viewmodel.PagedViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel
constructor(private val repo: JuHeRepository, private var apiRepo: ApiRepository) : PagedViewModel() {

    fun loadData() = repo.getNews("top", "6240d53186ae1b9e03bf737937258408")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getVersion() = apiRepo.getVersion()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}