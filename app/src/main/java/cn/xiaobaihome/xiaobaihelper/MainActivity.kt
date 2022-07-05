package cn.xiaobaihome.xiaobaihelper

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import cn.xiaobaihome.xiaobaihelper.api.ApiException
import cn.xiaobaihome.xiaobaihelper.api.Utils
import cn.xiaobaihome.xiaobaihelper.databinding.ActivityMainBinding
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseActivity
import cn.xiaobaihome.xiaobaihelper.mvvm.model.ApkInfo
import cn.xiaobaihome.xiaobaihelper.mvvm.model.RespResult
import cn.xiaobaihome.xiaobaihelper.mvvm.model.Status
import cn.xiaobaihome.xiaobaihelper.mvvm.view.openwrt.OpMainActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            launch {
                try {
                    viewModel.login("root", "Mcj19950324",object :
                        RespResult<String> {
                        override fun onSuccess(t: String) {
                            //alert(t)
                            startActivity(Intent(this@MainActivity,OpMainActivity::class.java))
                        }

                        override fun onError(msg: String) {
                            alert(msg)
                        }

                    })
                } catch (e: Exception) {
                    alert(e.toString())
                }
            }
        }

    }
}
