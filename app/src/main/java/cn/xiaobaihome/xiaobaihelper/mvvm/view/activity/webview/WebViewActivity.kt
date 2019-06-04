package cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.webview

import android.annotation.SuppressLint
import android.view.View
import android.webkit.*
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.base.BaseActivity
import cn.xiaobaihome.xiaobaihelper.databinding.ActivityWebviewBinding

class WebViewActivity : BaseActivity<ActivityWebviewBinding>(){

    private var url: String? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_webview
    }

    override fun initView() {
        url = intent.getStringExtra("url")
        initWebView()
        loadUrl()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        val settings = binding.webviewActivityWebview.settings
        settings.loadWithOverviewMode = true
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.builtInZoomControls = true
        settings.displayZoomControls = true
        settings.supportZoom()
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        settings.domStorageEnabled = true//开启DOM
        settings.defaultTextEncodingName = "utf-8"
        binding.webviewActivityWebview.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                return true
            }

        }
        binding.webviewActivityWebview.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                supportActionBar?.title = title
                super.onReceivedTitle(view, title)
            }
        }
    }

    private fun loadUrl() {
        // 加载Web地址
        if (url?.isNotEmpty()!!) {
            binding.webviewActivityWebview.loadUrl(url)
        }
    }

    override fun loadData(isRefresh: Boolean) {
    }

    override fun onClick(v: View?) {

    }

}
