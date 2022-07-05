package cn.xiaobaihome.xiaobaihelper.webview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import cn.xiaobaihome.xiaobaihelper.helper.getUA

@SuppressLint("SetJavaScriptEnabled")
class XBWebView constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defAttrStyle: Int = 0,
    defStyleRes: Int = 0
) : WebView(context, attributeSet, defAttrStyle, defStyleRes) {

    constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defAttrStyle: Int = 0
    ) : this(context, attributeSet, defAttrStyle, 0)

    constructor(
        context: Context,
        attributeSet: AttributeSet? = null
    ) : this(context, attributeSet, 0)

    constructor(context: Context) : this(context, null)

    init {
        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.setSupportMultipleWindows(true)
        //将图片调整到适合webview的大小
        settings.useWideViewPort = true
        // 缩放至屏幕的大小
        settings.loadWithOverviewMode = true
        //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.builtInZoomControls = true
        settings.setSupportZoom(true)
        settings.displayZoomControls = false
        //设置编码格式
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.allowFileAccess = true //设置可以访问文件

        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.loadsImagesAutomatically = true //支持自动加载图片

        settings.defaultTextEncodingName = "utf-8"
//        settings.pluginState = WebSettings.PluginState.ON
        settings.mediaPlaybackRequiresUserGesture = false
//        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        settings.domStorageEnabled = true
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        val userAgent = this.settings.userAgentString + "|" + getUA()
        settings.userAgentString = userAgent
        settings.textZoom = 100
        //开启调试模式
        setWebContentsDebuggingEnabled(true)

        val webSettings: WebSettings = settings
        webSettings.databaseEnabled = true
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
    }

}