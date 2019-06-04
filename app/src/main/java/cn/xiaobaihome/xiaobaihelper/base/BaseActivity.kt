package cn.xiaobaihome.xiaobaihelper.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import cn.xiaobaihome.xiaobaihelper.BR

abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity(),Presenter {

    protected val  binding: VB by lazy { DataBindingUtil.setContentView<VB>(this, getLayoutId()) }
    protected lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        binding.setVariable(BR.presenter, this)
        binding.executePendingBindings()
        binding.setLifecycleOwner(this)
        context = this
        initView()
//        lifecycle.addObserver(BaseActivityLifecycle(this))
    }

    abstract fun getLayoutId():Int
    abstract fun initView()
}