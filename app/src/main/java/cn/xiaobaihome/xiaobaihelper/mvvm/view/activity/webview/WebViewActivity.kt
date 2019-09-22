package cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.webview

import android.annotation.SuppressLint
import android.view.View
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.base.BaseActivity
import cn.xiaobaihome.xiaobaihelper.databinding.ActivityWebviewBinding
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

class WebViewActivity : BaseActivity<ActivityWebviewBinding>(){

    private var url: String? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_webview
    }

    override fun initView() {
        url = intent.getStringExtra("url")
        initWebView()
        loadUrl()

        binding.webViewActivityToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        val settings = binding.webViewActivityWebView.settings
        settings.loadWithOverviewMode = true
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.builtInZoomControls = true
        settings.displayZoomControls = true
        settings.supportZoom()
        //settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        settings.domStorageEnabled = true//开启DOM
        settings.defaultTextEncodingName = "utf-8"
        binding.webViewActivityWebView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                return true
            }

        }
        binding.webViewActivityWebView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                binding.webViewActivityTitle.text = title
                super.onReceivedTitle(view, title)
            }
        }
    }

    private fun loadUrl() {
        // 加载Web地址
        if (url?.isNotEmpty()!!) {
            binding.webViewActivityWebView.loadUrl(url)
        }
    }

    override fun loadData(isRefresh: Boolean) {
    }

    override fun onClick(v: View?) {

    }

}
