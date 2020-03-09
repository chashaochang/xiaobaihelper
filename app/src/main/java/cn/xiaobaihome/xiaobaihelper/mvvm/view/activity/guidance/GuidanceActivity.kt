package cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.guidance

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.base.BaseActivity
import cn.xiaobaihome.xiaobaihelper.databinding.ActivityGuidanceBinding
import cn.xiaobaihome.xiaobaihelper.helper.extens.dpToPx
import cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.home.HomeFragment
import cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.todo.TodoFragment
import cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.video.VideoFragment
import com.fm.openinstall.OpenInstall
import com.fm.openinstall.listener.AppWakeUpAdapter
import com.fm.openinstall.model.AppData
import com.tbruyelle.rxpermissions2.RxPermissions
import es.dmoral.toasty.Toasty
import kotlin.system.exitProcess

class GuidanceActivity : BaseActivity<ActivityGuidanceBinding>() {

    private var rxPermissions: RxPermissions? = null
    private var exitTime: Long = 0

    private var wakeUpAdapter: AppWakeUpAdapter = object : AppWakeUpAdapter() {
        override fun onWakeUp(appData: AppData) { //获取渠道数据
            val channelCode = appData.getChannel()
            //获取绑定数据
            val bindData = appData.getData()
            Log.d("OpenInstall", "getWakeUp : wakeupData = $appData")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //获取唤醒参数
        OpenInstall.getWakeUp(intent, wakeUpAdapter)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // 此处要调用，否则App在后台运行时，会无法截获
        OpenInstall.getWakeUp(intent, wakeUpAdapter)
    }

    override fun onClick(v: View?) {

    }

    override fun loadData(isRefresh: Boolean) {

    }

    private var homeFragment: HomeFragment? = null
    private var todoFragment: TodoFragment? = null
    private var videoFragment: VideoFragment? = null

    private var currentFragment: Fragment? = null
    private val fragments = SparseArray<Fragment>()
    private val navTexts = listOf("首页", "日程", "影视")
    private val navIconsChecked = listOf(R.mipmap.home_checked, R.mipmap.todo_checked, R.mipmap.movie_checked)
    private val navIconsUnChecked = listOf(R.mipmap.home_unchecked, R.mipmap.todo_unchecked, R.mipmap.movie_unchecked)

    @SuppressLint("AutoDispose", "CheckResult")
    override fun initView() {
        rxPermissions = RxPermissions(this)
        rxPermissions!!.requestEachCombined(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe {
                    // will emit 1 Permission object
                    if (it.granted) {
                        // All permissions are granted !
                    } else if (it.shouldShowRequestPermissionRationale) {
                        // At least one denied permission without ask never again

                    } else {
                        // At least one denied permission with ask never again
                        // Need to go to the settings
                    }
                }
        initFragments()
        initBottomNavigationView()

    }

    private fun initFragments() {
        if (homeFragment == null) {
            homeFragment = HomeFragment()
            currentFragment = homeFragment
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.guidance_activity_FrameLayout, homeFragment!!)
                    .commitAllowingStateLoss()
        }
        if (todoFragment == null) {
            todoFragment = TodoFragment()
        }
        if (videoFragment == null) {
            videoFragment = VideoFragment()
        }

        fragments.put(0, homeFragment)
        fragments.put(1, todoFragment)
        fragments.put(2, videoFragment)
    }

    private fun initBottomNavigationView() {
        for (index in 0 until binding.guidanceActivityBottomNavBar.childCount) {
            binding.guidanceActivityBottomNavBar.getChildAt(index).setOnClickListener {
                changeBottomIndex(index)
            }
        }
        homeFragment?.userVisibleHint = true
    }


    fun changeBottomIndex(index: Int) {
        when (index) {
            0 -> homeFragment?.let { toggleShowFragment(it) }
            1 -> todoFragment?.let { toggleShowFragment(it) }
            2 -> videoFragment?.let { toggleShowFragment(it) }
        }
        changeBottomItem(index)
    }

    //修改item样式
    private fun changeBottomItem(index: Int) {
        //
        for (i in 0 until binding.guidanceActivityBottomNavBar.childCount) {
            val itemLinear = binding.guidanceActivityBottomNavBar.getChildAt(i) as LinearLayout
            val textView = itemLinear.getChildAt(0) as TextView
            if (i == index) {//选中项
                textView.text = navTexts[i]
                val drawable = getDrawable(navIconsChecked[i])
                drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                textView.setCompoundDrawables(drawable, null, null, null)
                textView.compoundDrawablePadding = dpToPx(10)
                textView.background = getDrawable(R.drawable.bg_item_nav)
            } else {//未选中
                textView.text = ""
                val drawable = getDrawable(navIconsUnChecked[i])
                drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                textView.setCompoundDrawables(drawable, null, null, null)
                textView.compoundDrawablePadding = 0
                textView.setBackgroundColor(0xffffff)
            }
        }


    }

    override fun getLayoutId(): Int {
        return R.layout.activity_guidance
    }

    //切换显示fragment
    private fun toggleShowFragment(fragment: Fragment) {
        if (fragment.isAdded) {
            currentFragment?.let {
                supportFragmentManager.beginTransaction()
                        .hide(it)
                        .show(fragment)
                        .commitNowAllowingStateLoss()
            }
        } else {
            currentFragment?.let {
                supportFragmentManager.beginTransaction()
                        .add(R.id.guidance_activity_FrameLayout, fragment)
                        .hide(it)
                        .commitNowAllowingStateLoss()
            }
        }
        currentFragment = fragment
        currentFragment!!.userVisibleHint = true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toasty.normal(this, "再按一次退出程序").show()
                exitTime = System.currentTimeMillis()
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                exitProcess(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}