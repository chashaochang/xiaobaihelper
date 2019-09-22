package cn.xiaobaihome.xiaobaihelper.helper.binds

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.xiaobaihome.xiaobaihelper.base.Presenter
import cn.xiaobaihome.xiaobaihelper.mvvm.viewmodel.PagedViewModel
import com.bumptech.glide.Glide

/**
 * 页面描述：normal bind class
 *
 * Created by ditclear on 2017/10/2.
 */


@BindingAdapter(value = ["url"], requireAll = false)
fun bindUrl(imageView: ImageView, url: String?) {

    Glide.with(imageView.context)
            .load(url)
            .into(imageView)
}

@BindingAdapter(value = ["visible"])
fun bindVisibility(v: View, visible: Boolean) {
    v.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter(value = ["loadMore","loadMorePresenter"])
fun bindLoadMore(v: RecyclerView, vm: PagedViewModel?, presenter: Presenter) {
    v.layoutManager = LinearLayoutManager(v.context)
    v.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (recyclerView.layoutManager is LinearLayoutManager) {
                //表示是否能向上滚动，false表示已经滚动到底部
                //防止多次拉取同样的数据
                if (!recyclerView.canScrollVertically(1)) {
                    vm?.let {
                        if (vm.loadMore.get() && !vm.loading.get()) {
                            presenter.loadData(false)
                        }
                    }
                }
            }
        }
    })
}

@BindingAdapter(value = ["onRefresh"])
fun bindOnRefresh(v: SwipeRefreshLayout, presenter: Presenter) {
    v.setOnRefreshListener { presenter.loadData(true) }
}

@BindingAdapter(value = ["vertical"], requireAll = false)
fun bindSlider(v: RecyclerView, vertical: Boolean = true) {

    if (vertical) {
        v.layoutManager = LinearLayoutManager(v.context, LinearLayoutManager.VERTICAL, false)
    } else {
        if (v.onFlingListener == null) {
            PagerSnapHelper().attachToRecyclerView(v)
        }
        v.layoutManager = LinearLayoutManager(v.context, LinearLayoutManager.HORIZONTAL, false)
    }
}