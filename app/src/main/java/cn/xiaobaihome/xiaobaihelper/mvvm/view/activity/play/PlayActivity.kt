package cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.play

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.PixelFormat
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.base.BaseActivity
import cn.xiaobaihome.xiaobaihelper.databinding.ActivityPlayBinding
import cn.xiaobaihome.xiaobaihelper.helper.Constant
import com.baoyz.actionsheet.ActionSheet
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

class PlayActivity : BaseActivity<ActivityPlayBinding>() {

    /**
     * 视频全屏参数
     */
    private val COVER_SCREEN_PARAMS = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    private var customView: View? = null
    private var fullscreenContainer: FrameLayout? = null
    private var customViewCallback: IX5WebChromeClient.CustomViewCallback? = null

    private var url: String? = null
    private var title: String? = null
    internal var jxUrl = Constant.JXURL1
    override fun getLayoutId(): Int {
        return R.layout.activity_play
    }

    override fun initView() {
        //视频为了避免闪屏和透明问题
        window.setFormat(PixelFormat.TRANSLUCENT)
        url = intent.getStringExtra("url")
        title = intent.getStringExtra("title")
        binding.playActivityTitle.text = title
        initWebView()
        binding.playActivityChangeLineBtn.setOnClickListener {
            ActionSheet.createBuilder(this, supportFragmentManager)
                    .setCancelButtonTitle("取消")
                    .setOtherButtonTitles("线路一", "线路二", "线路三", "线路四", "线路五")
                    .setCancelableOnTouchOutside(true)
                    .setListener(actionSheetListener)
                    .show()
        }
        binding.playActivityToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private val actionSheetListener = object : ActionSheet.ActionSheetListener {
        override fun onDismiss(actionSheet: ActionSheet, isCancel: Boolean) {

        }

        override fun onOtherButtonClick(actionSheet: ActionSheet, index: Int) {
            when (index) {
                0 -> {
                    jxUrl = Constant.JXURL1
                    loadUrl()
                }
                1 -> {
                    jxUrl = Constant.JXURL2
                    loadUrl()
                }
                2 -> {
                    jxUrl = Constant.JXURL3
                    loadUrl()
                }
                3 -> {
                    jxUrl = Constant.JXURL4
                    loadUrl()
                }
                4 -> {
                    jxUrl = Constant.JXURL5
                    loadUrl()
                }
                else -> {
                }
            }
        }
    }

    /**
     * 展示网页界面
     */
    @SuppressLint("SetJavaScriptEnabled")
    fun initWebView() {
        val webSettings = binding.playActivityWebview.settings
        webSettings.allowFileAccess = true // 允许访问文件
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE // 不加载缓存内容
        webSettings.loadWithOverviewMode = true
        webSettings.javaScriptEnabled = true
        webSettings.useWideViewPort = true
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = true
        webSettings.supportZoom()
        //webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        webSettings.domStorageEnabled = true//开启DOM
        webSettings.defaultTextEncodingName = "utf-8"
        webSettings.mediaPlaybackRequiresUserGesture = false
        val wvcc = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                binding.playActivityTitle.text = title
            }
        }
        binding.playActivityWebview.webChromeClient = wvcc
        val wvc = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                Log.i("url", url)
                //防止跳转广告
                if (!url.contains(this@PlayActivity.url!!.substring(this@PlayActivity.url!!.length - 10, this@PlayActivity.url!!.length))) {
                    Log.i("return", url)
                    return true
                }
                Log.i("load", url)
                binding.playActivityWebview.loadUrl(url)
                return false
            }

//            @SuppressLint("DefaultLocale")
//            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
//                val reqUrl = request?.url.toString().toLowerCase()
//                return if (!reqUrl.contains(url.toString())) {
//                    if (!hasAd(context, reqUrl)) {
//                        super.shouldInterceptRequest(view, reqUrl)
//                    } else {
//                        WebResourceResponse(null, null, null)
//                    }
//                } else {
//                    super.shouldInterceptRequest(view, reqUrl)
//                }
//            }
        }
        binding.playActivityWebview.webViewClient = wvc

        binding.playActivityWebview.webChromeClient = object : WebChromeClient() {

            /*** 视频播放相关的方法  */

            override fun getVideoLoadingProgressView(): View? {
                val frameLayout = FrameLayout(this@PlayActivity)
                frameLayout.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                return frameLayout
            }

            override fun onShowCustomView(view: View, callback: IX5WebChromeClient.CustomViewCallback) {
                Log.i("fangfa", "已经进入了。。。。。。。。")
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                showCustomView(view, callback)
            }

            override fun onHideCustomView() {
                hideCustomView()
            }
        }

        loadUrl()

    }

    private fun loadUrl() {
        // 加载Web地址
        if (url != null && url!!.isNotEmpty()) {
            binding.playActivityWebview.loadUrl(jxUrl + url)
        }
    }

    /**
     * 视频播放全屏
     */
    private fun showCustomView(view: View, callback: IX5WebChromeClient.CustomViewCallback) {
        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden()
            return
        }
        //横屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val decor = window.decorView as FrameLayout
        fullscreenContainer = FullscreenHolder(this)
        (fullscreenContainer as FullscreenHolder).addView(view, COVER_SCREEN_PARAMS)
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS)
        customView = view
        setStatusBarVisibility(false)
        customViewCallback = callback
    }

    /**
     * 隐藏视频全屏
     */
    private fun hideCustomView() {
        if (customView == null) {
            return
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//竖屏
        setStatusBarVisibility(true)
        val decor = window.decorView as FrameLayout
        decor.removeView(fullscreenContainer)
        fullscreenContainer = null
        customView = null
        customViewCallback?.onCustomViewHidden()
        binding.playActivityWebview.visibility = View.VISIBLE
    }

    /**
     * 全屏容器界面
     */
    internal class FullscreenHolder(ctx: Context) : FrameLayout(ctx) {

        init {
            setBackgroundColor(ctx.resources.getColor(android.R.color.black))
        }

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouchEvent(evt: MotionEvent): Boolean {
            return true
        }
    }

    private fun setStatusBarVisibility(visible: Boolean) {
        val flag = if (visible) 0 else WindowManager.LayoutParams.FLAG_FULLSCREEN
        window.setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                /* 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
                if (customView != null) {
                    hideCustomView()
                } else {
                    finish()
                }
                true
            }
            else -> super.onKeyUp(keyCode, event)
        }
    }

    override fun loadData(isRefresh: Boolean) {

    }

    override fun onClick(v: View?) {

    }

    fun hasAd(context: Context, url: String): Boolean {
        val res = context.resources
        val adUrls = res.getStringArray(R.array.adBlockUrl)
        for (adUrl in adUrls) {
            if (url.contains(adUrl)) {
                return true
            }
        }
        return false
    }
}