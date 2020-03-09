package cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.todo

import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.base.BaseFragment
import cn.xiaobaihome.xiaobaihelper.databinding.FragmentTodoBinding

class TodoFragment : BaseFragment<FragmentTodoBinding>() {
    override fun initView() {

    }

    override fun loadData(isRefresh: Boolean) {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_todo
    }
}