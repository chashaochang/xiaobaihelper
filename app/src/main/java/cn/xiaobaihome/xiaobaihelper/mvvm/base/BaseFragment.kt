package cn.xiaobaihome.xiaobaihelper.mvvm.base

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseFragment : Fragment() {

    private lateinit var alertDialog: AlertDialog

    protected fun alert(msg: String) {
        alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("提示")
            .setMessage(msg)
            .show()
    }

    protected fun alert(msg: String, okBtnText: String, listener: DialogInterface.OnClickListener) {
        alertDialog = context?.let {
            AlertDialog.Builder(it)
                .setTitle("提示")
                .setMessage(msg)
                .setNegativeButton(okBtnText, listener)
                .show()
        }!!
    }

    fun launch(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch { block() }
    }

}