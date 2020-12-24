package cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.webview

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import cn.xiaobaihome.xiaobaihelper.databinding.ActivityWebviewBinding
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseActivity
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

class WebViewActivity : BaseActivity(){

    private var url: String? = null
    private lateinit var binding : ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setTaskDescription(ActivityManager.TaskDescription("小程序"))
        url = intent.getStringExtra("url")
        Log.i(TAG, "initView: url:$url")
        initWebView()
        loadUrl()

        binding.webViewActivityBack.setOnClickListener {
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
                Log.i("openUrl", "shouldOverrideUrlLoading: "+request.url)
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

    companion object {
        private const val TAG = "WebViewActivity"
    }

}
