package cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.guidance

import android.util.SparseArray
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.base.BaseActivity
import cn.xiaobaihome.xiaobaihelper.databinding.ActivityGuidanceBinding
import cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.home.HomeFragment
import cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.mine.MineFragment
import cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.video.VideoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class GuidanceActivity : BaseActivity<ActivityGuidanceBinding>() {

    override fun onClick(v: View?) {

    }

    override fun loadData(isRefresh: Boolean) {

    }

    private var homeFragment: HomeFragment? = null
    private var videoFragment: VideoFragment? = null
    private var mineFragment: MineFragment? = null
    private var currentFragment: Fragment? = null
    private val fragments = SparseArray<Fragment>()

    override fun initView() {
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
        binding.guidanceActivityBottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    homeFragment?.let { toggleShowFragment(it) }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_video -> {
                    videoFragment?.let { toggleShowFragment(it) }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_mine -> {
                    mineFragment?.let { toggleShowFragment(it) }
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
        homeFragment?.userVisibleHint = true
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