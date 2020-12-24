package cn.xiaobaihome.xiaobaihelper.mvvm.base

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import cn.xiaobaihome.xiaobaihelper.helper.annotation.ToastType
import cn.xiaobaihome.xiaobaihelper.helper.extens.dispatchFailure
import cn.xiaobaihome.xiaobaihelper.helper.extens.toast

abstract class BaseFragment : Fragment() {

    protected var lazyLoad = false

    protected var visible = false

    private lateinit var alertDialog: AlertDialog

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = true
    }

    protected fun alert(msg: String) {
        alertDialog = context?.let {
            AlertDialog.Builder(it)
                    .setTitle("提示")
                    .setMessage(msg)
                    .show()
        }!!
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

    fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun toastSuccess(msg: String?) {
        msg?.let { activity?.toast(it, ToastType.SUCCESS) }
    }

    fun toastFailure(error: Throwable) {
        activity?.dispatchFailure(error)
    }

    fun getVersion(): Int {
        val info = activity?.packageManager?.getPackageInfo(activity?.packageName.toString(), 0)
        if (info?.longVersionCode == null) {
            return 0
        }
        return info.longVersionCode.toInt()
    }

}