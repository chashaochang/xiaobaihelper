package cn.xiaobaihome.xiaobaihelper.mvvm.base

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

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

    fun getVersion(): Int {
        val info = activity?.packageManager?.getPackageInfo(activity?.packageName.toString(), 0)
        if (info?.longVersionCode == null) {
            return 0
        }
        return info.longVersionCode.toInt()
    }

}