package cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.guidance

import android.Manifest
import android.annotation.SuppressLint
import android.util.SparseArray
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.base.BaseActivity
import cn.xiaobaihome.xiaobaihelper.databinding.ActivityGuidanceBinding
import cn.xiaobaihome.xiaobaihelper.helper.extens.dpToPx
import cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.home.HomeFragment
import cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.mine.MineFragment
import cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.video.VideoFragment
import com.tbruyelle.rxpermissions2.RxPermissions


class GuidanceActivity : BaseActivity<ActivityGuidanceBinding>() {

    var rxPermissions: RxPermissions? = null

    override fun onClick(v: View?) {

    }

    override fun loadData(isRefresh: Boolean) {

    }

    private var homeFragment: HomeFragment? = null
    private var videoFragment: VideoFragment? = null
    private var mineFragment: MineFragment? = null
    private var currentFragment: Fragment? = null
    private val fragments = SparseArray<Fragment>()
    private val navTexts = listOf("首页","影视","我的")
    private val navIconsChecked = listOf(R.mipmap.home_checked,R.mipmap.movie_checked,R.mipmap.mine_checked)
    private val navIconsUnChecked = listOf(R.mipmap.home_unchecked,R.mipmap.movie_unchecked,R.mipmap.mine_unchecked)

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
        if (videoFragment == null) {
            videoFragment = VideoFragment()
        }
        if (mineFragment == null) {
            mineFragment = MineFragment()
        }

        val HOME_POSITION = 0
        val VIDEO_POSITION = 1
        val MINE_POSITION = 2

        fragments.put(HOME_POSITION, homeFragment)
        fragments.put(VIDEO_POSITION, videoFragment)
        fragments.put(MINE_POSITION, mineFragment)
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
            1 -> videoFragment?.let { toggleShowFragment(it) }
            2 -> mineFragment?.let { toggleShowFragment(it) }
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
                textView.setCompoundDrawables(drawable,null,null,null)
                textView.compoundDrawablePadding = dpToPx(5)
                textView.background = getDrawable(R.drawable.bg_item_nav)
            } else {//未选中
                textView.text = ""
                val drawable = getDrawable(navIconsUnChecked[i])
                drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                textView.setCompoundDrawables(drawable,null,null,null)
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

}