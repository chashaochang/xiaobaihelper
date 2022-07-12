package cn.xiaobaihome.xiaobaihelper.mvvm.base

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import cn.xiaobaihome.xiaobaihelper.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    fun toast(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun launch(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch { block() }
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.push_up_in, R.anim.push_none)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.push_none, R.anim.push_up_out)
    }

}