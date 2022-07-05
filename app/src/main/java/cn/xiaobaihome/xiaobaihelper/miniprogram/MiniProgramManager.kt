package cn.xiaobaihome.xiaobaihelper.miniprogram

import android.app.Activity
import android.content.Context
import android.content.Intent
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.mvvm.view.webview.MiniProgramWebViewActivity

/*
     * pool中有5个webView进程0-4
     * 用到了就从pool里拿一个
     * activity销毁后放回pool里
     */
object MiniProgramManager {

    //未使用的webView池
    private var webViewPool: MutableList<String> = ArrayList()
    var activatedWebViews: LinkedHashMap<String, String> = LinkedHashMap()
    var activityMiniPrograms: MutableList<MiniProgramWebViewActivity> = ArrayList()

    init {
        webViewPool.add("cn.xiaobaihome.xiaobaihelper.mvvm.view.webview.WebViewActivity\$AppBrandUI0")
        webViewPool.add("cn.xiaobaihome.xiaobaihelper.mvvm.view.webview.WebViewActivity\$AppBrandUI1")
        webViewPool.add("cn.xiaobaihome.xiaobaihelper.mvvm.view.webview.WebViewActivity\$AppBrandUI2")
        webViewPool.add("cn.xiaobaihome.xiaobaihelper.mvvm.view.webview.WebViewActivity\$AppBrandUI3")
        webViewPool.add("cn.xiaobaihome.xiaobaihelper.mvvm.view.webview.WebViewActivity\$AppBrandUI4")
    }

    fun openMiniProgram(context: Context, miniProgramId: String, name: String, iconRes: Int) {
        var needSuffix = true
        var className = activatedWebViews[miniProgramId]
        if (className.isNullOrBlank()) {
            if (webViewPool.size > 0) {//池子里有就拿
                className = webViewPool[0]
                webViewPool.removeAt(0)
                activatedWebViews[miniProgramId] = className
            } else {//池子里没有了，就把最早的干掉，放入新的
                val webView = activatedWebViews.entries.iterator().next()
                className = webView.value
                activatedWebViews[miniProgramId] = className
                activatedWebViews.remove(webView.key)
                needSuffix = false
            }
        }else{
            needSuffix = false
        }
        val intent = Intent().apply {
            setClassName(
                context,
                className
            )
        }
        intent.putExtra("miniProgramId", miniProgramId)
        intent.putExtra("title", name)
        intent.putExtra("icon", iconRes)
        intent.putExtra("needSuffix", needSuffix)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        context.startActivity(intent)
        val activity = context as Activity
        activity.overridePendingTransition(R.anim.slide_bottom_in, R.anim.push_none)
    }


}


