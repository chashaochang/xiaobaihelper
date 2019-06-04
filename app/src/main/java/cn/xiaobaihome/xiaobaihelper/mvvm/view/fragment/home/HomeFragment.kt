package cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.home

import android.content.Intent
import android.graphics.Rect
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.base.BaseFragment
import cn.xiaobaihome.xiaobaihelper.databinding.FragmentHomeBinding
import cn.xiaobaihome.xiaobaihelper.helper.adapter.recyclerview.ItemClickPresenter
import cn.xiaobaihome.xiaobaihelper.helper.adapter.recyclerview.SingleTypeAdapter
import cn.xiaobaihome.xiaobaihelper.helper.extens.bindLifeCycle
import cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.webview.WebViewActivity
import cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.home.viewmodel.HomeViewModel
import cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.home.viewmodel.NewItemViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

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
        lazyLoad = true
        binding.vm = homeViewModel
        binding.homeFragmentRecyclerView.apply {
            adapter = homeAdapter
            isPrepared = true
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun loadData(isRefresh: Boolean) {
        homeViewModel.loadData(isRefresh).bindLifeCycle(this).subscribe({
            it.result?.data1?.map(::NewItemViewModel)?.let {
                if (isRefresh) {
                    list.clear()
                }
                with(list) { addAll(it) }
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