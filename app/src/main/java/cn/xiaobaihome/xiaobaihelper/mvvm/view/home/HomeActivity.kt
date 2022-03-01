package cn.xiaobaihome.xiaobaihelper.mvvm.view.home

import android.os.Bundle
import android.util.SparseArray
import android.view.KeyEvent
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.databinding.ActivityHomeBinding
import cn.xiaobaihome.xiaobaihelper.helper.extens.dpToPx
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseActivity
import cn.xiaobaihome.xiaobaihelper.mvvm.model.ApkInfo
import cn.xiaobaihome.xiaobaihelper.mvvm.model.RespResult
import cn.xiaobaihome.xiaobaihelper.api.Utils
import cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.todo.TodoFragment
import cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.video.VideoFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private var exitTime: Long = 0
    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFragments()
        initBottomNavigationView()

        launch {
            homeViewModel.getVersion(object :
                RespResult<ApkInfo> {
                override fun onSuccess(t: ApkInfo) {

                    if (t.versionCode > getVersion()) {
                        alert(t.updateInfo.toString(), "更新") { _, _ ->
                            Utils.downLoadNew(this@HomeActivity, t.downloadUrl)
                        }
                    }
                }

                override fun onError(msg: String) {
                    //alert(msg)
                }

            })
        }

    }

    fun getVersion(): Int {
        val info = packageManager?.getPackageInfo(packageName.toString(), 0)
        if (info?.longVersionCode == null) {
            return 0
        }
        return info.longVersionCode.toInt()
    }

    private var homeFragment: HomeFragment? = null
    private var todoFragment: TodoFragment? = null
    private var videoFragment: VideoFragment? = null

    private var currentFragment: Fragment? = null
    private val fragments = SparseArray<Fragment>()
    private val navTexts = listOf("首页", "日程", "影视")
    private val navIconsChecked =
        listOf(R.mipmap.home_checked, R.mipmap.todo_checked, R.mipmap.movie_checked)
    private val navIconsUnChecked =
        listOf(R.mipmap.home_unchecked, R.mipmap.todo_unchecked, R.mipmap.movie_unchecked)

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
                val drawable = AppCompatResources.getDrawable(this, navIconsChecked[i])
                drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                textView.setCompoundDrawables(drawable, null, null, null)
                textView.compoundDrawablePadding = dpToPx(10)
                textView.background = AppCompatResources.getDrawable(this, R.drawable.bg_item_nav)
            } else {//未选中
                textView.text = ""
                val drawable = AppCompatResources.getDrawable(this, navIconsUnChecked[i])
                drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                textView.setCompoundDrawables(drawable, null, null, null)
                textView.compoundDrawablePadding = 0
                textView.setBackgroundColor(0xffffff)
            }
        }


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
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if (System.currentTimeMillis() - exitTime > 2000) {
                toast("再按一次退出程序")
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