package cn.xiaobaihome.xiaobaihelper.helper.extens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import cn.xiaobaihome.xiaobaihelper.BuildConfig
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.helper.Constant
import java.io.Serializable


/**
 * 页面描述：一些扩展
 *
 * Created by ditclear on 2017/9/29.
 */

fun Activity.getCompactColor(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

private var toast: Toast? = null

@SuppressLint("InflateParams")
fun toast(context: Context, msg: String?) {
    try {
        if (toast != null) {
            toast!!.cancel()
        }
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        val toastLayout =
            (context.getSystemService(AppCompatActivity.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.layout_toast, null)
        val textView = toastLayout.findViewById<TextView>(R.id.toast_text)
        textView.text = msg
        toast?.view = toastLayout
        toast?.setGravity(Gravity.CENTER, 0, 0)
        toast?.show()
    } catch (e: Exception) {
        Log.i("Extends", "alert: $e")
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun dpToPx(dpValue: Int): Int {
    return (0.5f + dpValue * Resources.getSystem().displayMetrics.density).toInt()
}

fun Activity.navigateToActivity(c: Class<*>, serializable: Serializable? = null) {
    val intent = Intent()
    serializable?.let {
        val bundle = Bundle()
        bundle.putSerializable(Constant.KEY_SERIALIZABLE, it)
        intent.putExtras(bundle)
    }
    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle()

    intent.setClass(this, c)
    startActivity(intent, options)
}

fun Any.logD(msg: String?) {
    if (BuildConfig.DEBUG) {
        Log.d(javaClass.simpleName, msg!!)
    }
}

fun getStatusBarHeight(context: Context): Int {
    val resources = context.resources
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
}


//////////////////////////LiveData///////////////////////////////////

fun <T> MutableLiveData<T>.set(t: T?) = this.postValue(t)
fun <T> MutableLiveData<T>.get() = this.value

fun <T> MutableLiveData<T>.get(t: T): T = get() ?: t

fun <T> MutableLiveData<T>.init(t: T) = MutableLiveData<T>().apply {
    postValue(t)
}