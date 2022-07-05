package cn.xiaobaihome.xiaobaihelper.mvvm.view.openwrt

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OpLoginFragment : BaseFragment() {

    companion object {
        fun newInstance() = OpLoginFragment()
    }

    private val viewModel: OpLoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.op_login_fragment, container, false)
    }



}