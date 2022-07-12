package cn.xiaobaihome.xiaobaihelper.mvvm.view.openwrt

import android.os.Bundle
import androidx.activity.viewModels
import cn.xiaobaihome.xiaobaihelper.databinding.ActivityOpMainBinding
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseActivity
import cn.xiaobaihome.xiaobaihelper.mvvm.model.RespResult
import cn.xiaobaihome.xiaobaihelper.mvvm.model.Status
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class OpMainActivity :BaseActivity() {

    lateinit var binding:ActivityOpMainBinding
    val viewModel:OpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    fun getStatus(){
        launch {
            try {
                viewModel.getStatus("1", Date().time.toString(),object :
                    RespResult<Status> {
                    override fun onSuccess(t: Status) {
                        //alert(Gson().toJson(t))

                    }

                    override fun onError(msg: String) {
                        //alert(msg)
                    }

                })
            } catch (e: Exception) {
                //alert(e.toString())
            }
        }
    }

}