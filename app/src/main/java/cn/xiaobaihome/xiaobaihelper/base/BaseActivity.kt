package cn.xiaobaihome.xiaobaihelper.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import cn.xiaobaihome.xiaobaihelper.BR
import android.graphics.Rect
import cn.xiaobaihome.xiaobaihelper.R


abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity(), Presenter {

    protected val binding: VB by lazy { DataBindingUtil.setContentView<VB>(this, getLayoutId()) }
    protected lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getDarkModeStatus(this)) {
            setTheme(R.style.AppThemeDark)
        } else {
            setTheme(R.style.AppTheme)
        }
        setContentView(getLayoutId())
        initStatusBar()
        binding.setVariable(BR.presenter, this)
        binding.executePendingBindings()
        binding.lifecycleOwner = this
        context = this
        initView()
        //lifecycle.addObserver(BaseActivityLifecycle(this))
    }

    private fun initStatusBar() {
        val decorView = window.decorView
        val option = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        decorView.systemUiVisibility = option
//        window.statusBarColor = getCompactColor(R.color.colorPrimary)


    }

    fun getStatusBarHeight(): Int {
        val rectangle = Rect()
        val window = window
        window.decorView.getWindowVisibleDisplayFrame(rectangle)
        return rectangle.top
    }

    fun setActionBarDark(color: Int) {
        val decorView = window.decorView
        val option = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or SYSTEM_UI_FLAG_LAYOUT_STABLE
        decorView.systemUiVisibility = option
        window.statusBarColor = color
    }

    abstract fun getLayoutId(): Int
    abstract fun initView()

    fun getVersion(): String {
        return try {
            val info = packageManager.getPackageInfo(packageName, 0)
            info.versionName
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    override fun loadData(isRefresh: Boolean) {

    }

    override fun onClick(v: View?) {

    }

    //检查当前系统是否已开启暗黑模式
    fun getDarkModeStatus(context: Context): Boolean {
        val mode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return mode == Configuration.UI_MODE_NIGHT_YES
    }

}