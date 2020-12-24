package cn.xiaobaihome.xiaobaihelper.helper.extens

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.webkit.URLUtil
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import cn.xiaobaihome.xiaobaihelper.BuildConfig
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.helper.Constant
import cn.xiaobaihome.xiaobaihelper.helper.annotation.ToastType
import cn.xiaobaihome.xiaobaihelper.mvvm.model.remote.exception.EmptyException
import es.dmoral.toasty.Toasty
import java.io.Serializable
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit


/**
 * 页面描述：一些扩展
 *
 * Created by ditclear on 2017/9/29.
 */

fun Activity.getCompactColor(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

fun Activity.toast(msg: CharSequence, duration: Int = Toast.LENGTH_SHORT, @ToastType type: Int = ToastType.NORMAL) {
    when (type) {
        ToastType.WARNING -> Toasty.warning(this, msg, duration, true).show()
        ToastType.ERROR -> Toasty.error(this, msg, duration, true).show()
        ToastType.NORMAL -> Toasty.info(this, msg, duration, false).show()
        ToastType.SUCCESS -> Toasty.success(this, msg, duration, true).show()
    }
}

fun Activity.dispatchFailure(error: Throwable?) {
    error?.let {
        if (BuildConfig.DEBUG) {
            it.printStackTrace()
        }
        if (it is EmptyException) {

        } else if (error is SocketTimeoutException) {
            it.message?.let { toast("网络连接超时", ToastType.ERROR) }

        } else if (it is UnknownHostException || it is ConnectException) {
            //网络未连接
            it.message?.let { toast("网络未连接", ToastType.ERROR) }

        } else {
            it.message?.let { toast(it, ToastType.ERROR) }
        }
    }
}

fun <T : Any> FragmentActivity.argument(key: String) =
        lazy {
            @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "UNCHECKED_CAST")
            intent.extras?.get(key) as? T ?: error("Intent Argument $key is missing")
        }

fun AppCompatActivity.switchFragment(current: Fragment?, targetFg: Fragment, tag: String? = null) {
    val ft = supportFragmentManager.beginTransaction()
    current?.run { ft.hide(this) }
    if (!targetFg.isAdded) {
        ft.add(R.id.container, targetFg, tag)
    }
    ft.show(targetFg)
    ft.commitAllowingStateLoss();
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

fun Activity.navigateToWebPage(@NonNull url: String) {
    if (TextUtils.isEmpty(url) || !URLUtil.isNetworkUrl(url)) {
        return
    }

    val intent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .build()

    intent.launchUrl(this, Uri.parse(url))
}

fun Fragment.navigateToWebPage(@NonNull url: String?) {
    if (TextUtils.isEmpty(url) || !URLUtil.isNetworkUrl(url)) {
        return
    }
    context?.let {
        val intent = CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setToolbarColor(ContextCompat.getColor(it, R.color.colorPrimary))
                .build()

        activity?.let { it1 -> intent.launchUrl(it1, Uri.parse(url)) }
    }
}

//////////////////////////LiveData///////////////////////////////////

fun <T> MutableLiveData<T>.set(t: T?) = this.postValue(t)
fun <T> MutableLiveData<T>.get() = this.value

fun <T> MutableLiveData<T>.get(t: T): T = get() ?: t

fun <T> MutableLiveData<T>.init(t: T) = MutableLiveData<T>().apply {
    postValue(t)
}