package cn.xiaobaihome.xiaobaihelper.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import cn.xiaobaihome.xiaobaihelper.factory.ViewModelFactory

abstract class BaseLifecycleActivity<T : BaseViewModel> : BaseActivity() {

    protected lateinit var viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = initViewModel()
        subscribeLiveData()
    }

    private fun initViewModel(): T {
        return ViewModelProviders.of(this, ViewModelFactory(application, arguments())).get(viewModelClass())
    }

    /**
     * viewModel的class
     */
    abstract fun viewModelClass(): Class<T>

    /**
     * 初始化viewModel的参数
     */
    abstract fun arguments(): Bundle

    abstract fun subscribeLiveData()

}