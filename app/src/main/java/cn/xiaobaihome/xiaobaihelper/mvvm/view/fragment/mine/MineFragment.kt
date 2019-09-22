package cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.mine

import android.content.Intent
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.base.BaseFragment
import cn.xiaobaihome.xiaobaihelper.databinding.FragmentMineBinding
import cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.login.LoginActivity

class MineFragment :BaseFragment<FragmentMineBinding>(){

    override fun initView() {
        binding.mineFragmentScrollView.setOnScrollChangeListener { _, _, _, _, p4 ->
            if (p4 <= 100) {
                binding.mineFragmentToolbar.text = ""
            } else {
                binding.mineFragmentToolbar.text = "我的"
            }
        }
        binding.fragmentMineBtnLogin.setOnClickListener {
            startActivity(Intent(context,LoginActivity::class.java))
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun loadData(isRefresh: Boolean) {

    }

}