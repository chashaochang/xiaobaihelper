package cn.xiaobaihome.xiaobaihelper.mvvm.base

import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseActivity : AppCompatActivity() {

    lateinit var context: Context

    private lateinit var alertDialog: AlertDialog

    //检查当前系统是否已开启暗黑模式
    private fun getDarkModeStatus(context: Context): Boolean {
        val mode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return mode == Configuration.UI_MODE_NIGHT_YES
    }

    fun toast(msg: String?) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    protected fun alert(msg: String) {
        alertDialog = AlertDialog.Builder(this)
            .setTitle("提示")
            .setMessage(msg)
            .show()
    }

    protected fun alert(msg: String, okBtnText: String, listener: DialogInterface.OnClickListener) {
        alertDialog = AlertDialog.Builder(this)
            .setTitle("提示")
            .setMessage(msg)
            .setNegativeButton(okBtnText, listener)
            .show()
    }

    fun launch(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch { block() }
    }

}