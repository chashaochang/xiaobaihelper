package cn.xiaobaihome.xiaobaihelper.mvvm.view.openwrt

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cn.xiaobaihome.xiaobaihelper.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OpMainFragment : Fragment() {

    companion object {
        fun newInstance() = OpMainFragment()
    }

    private val viewModel: OpMainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.op_main_fragment, container, false)
    }

}