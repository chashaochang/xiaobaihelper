package cn.xiaobaihome.xiaobaihelper.mvvm.view.schedule

import android.os.Bundle
import cn.xiaobaihome.xiaobaihelper.databinding.ActivityScheduleBinding
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleActivity : BaseActivity() {

    lateinit var binding : ActivityScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}