package cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.home

import android.content.DialogInterface
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.base.BaseFragment
import cn.xiaobaihome.xiaobaihelper.databinding.FragmentHomeBinding
import cn.xiaobaihome.xiaobaihelper.entity.Shortcut
import cn.xiaobaihome.xiaobaihelper.helper.adapter.recyclerview.ItemClickPresenter
import cn.xiaobaihome.xiaobaihelper.helper.adapter.recyclerview.SingleTypeAdapter
import cn.xiaobaihome.xiaobaihelper.helper.extens.bindLifeCycle
import cn.xiaobaihome.xiaobaihelper.mvvm.model.remote.Utils
import cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.food.FoodActivity
import cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.webview.WebViewActivity
import cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.home.viewmodel.HomeViewModel
import cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.home.viewmodel.NewItemViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import androidx.recyclerview.widget.LinearLayoutManager
import cn.xiaobaihome.xiaobaihelper.base.BaseActivity
import cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.guidance.GuidanceActivity

class HomeFragment : BaseFragment<FragmentHomeBinding>(), ItemClickPresenter<NewItemViewModel> {

    override fun onItemClick(v: View?, item: NewItemViewModel) {
        val intent = Intent()
        intent.setClass(activity!!, WebViewActivity::class.java)
        intent.putExtra("url", item.url)
        startActivity(intent)
    }

    private val homeAdapter by lazy {
        SingleTypeAdapter<NewItemViewModel>(mContext, R.layout.item_news, list).apply {
            itemPresenter = this@HomeFragment
        }
    }

    private val homeViewModel: HomeViewModel by viewModel()

    private val list = ObservableArrayList<NewItemViewModel>()

    override fun initView() {
        binding.homeFragmentScrollView.setOnScrollChangeListener { _, _, _, _, p4 ->
            if (p4 <= 100) {
                binding.homeFragmentToolbar.text = ""
                val array = activity?.theme?.obtainStyledAttributes(intArrayOf(R.attr.main_bg_color))
                val bgColor = array?.getColor(0, 0xffffff)
                bgColor?.let { binding.homeFragmentToolbar.setBackgroundColor(it) }
            } else {
                binding.homeFragmentToolbar.text = "小白助手"
                binding.homeFragmentToolbar.background = context?.let { ContextCompat.getDrawable(it, R.drawable.cell_border_bottom_ed) }
            }
        }
        lazyLoad = true
        binding.vm = homeViewModel
        binding.homeFragmentRecyclerView.apply {
            adapter = homeAdapter
            isPrepared = true
        }
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.isAutoMeasureEnabled = true
        binding.homeFragmentRecyclerView.layoutManager = linearLayoutManager

        val list = ArrayList<Shortcut>()
        val isDark = context?.let { (activity as BaseActivity<*>).getDarkModeStatus(it) }
        val shortcutVideo = Shortcut(if (isDark!!) R.mipmap.video_dark else R.mipmap.video, "影视", "video")
        val shortcutFood = Shortcut(if (isDark) R.mipmap.food_dark else R.mipmap.food, "美食", "food")
        list.add(shortcutVideo)
        list.add(shortcutFood)
        binding.homeFragmentGridView.adapter = HomeGridViewAdapter(list)
        binding.homeFragmentGridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, p2, _ ->
            when (p2) {
                0 -> {
                    val guidanceActivity = activity as GuidanceActivity
                    guidanceActivity.changeBottomIndex(1)
                }
                1 -> {
                    startActivity(Intent(context, FoodActivity::class.java))
                }
            }
        }
        homeViewModel.getVersion()
                .bindLifeCycle(this)
                .subscribe({
                    if (it.versionCode > getVersion()) {
                        alert(it.memo.toString(), "更新", DialogInterface.OnClickListener { _, _ ->
                            context?.let { it1 -> it.address?.let { it2 -> Utils.downLoadNew(it1, it2) } }
                        })
                    }
                }, {
                    toastFailure(it)
                })

    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun loadData(isRefresh: Boolean) {

        homeViewModel.loadData().bindLifeCycle(this).subscribe({
            it.result?.data1?.map(::NewItemViewModel)?.let {
                if (isRefresh) {
                    list.clear()

                }
                with(list) {
                    //这里我们取前五条
                    addAll(it.subList(0, 5))
                }
            }
        }, {
            toastFailure(it)
        })

    }

    override fun lazyLoad() {
        if (!isPrepared || !visible || hasLoadOnce) {
            return
        }
        hasLoadOnce = true
        loadData(true)
    }

}