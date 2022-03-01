package cn.xiaobaihome.xiaobaihelper.mvvm.view.webview

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Application
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.webkit.*
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.databinding.ActivityWebviewBinding
import cn.xiaobaihome.xiaobaihelper.helper.extens.hide
import cn.xiaobaihome.xiaobaihelper.helper.extens.show
import cn.xiaobaihome.xiaobaihelper.helper.getJson
import cn.xiaobaihome.xiaobaihelper.miniprogram.AppConfig
import cn.xiaobaihome.xiaobaihelper.miniprogram.MiniProgramManager
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class WebViewActivity : BaseActivity() {

    class AppBrandUI0 : WebViewActivity()
    class AppBrandUI1 : WebViewActivity()
    class AppBrandUI2 : WebViewActivity()
    class AppBrandUI3 : WebViewActivity()
    class AppBrandUI4 : WebViewActivity()

    private val basePath = "file:///android_asset/miniprogram/"

    private var url: String? = null
    private var title: String? = null
    private var icon = 0
    private var needSuffix: Boolean = true
    private var miniProgramId: String? = null

    private lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val processName = Application.getProcessName()
        url = intent.getStringExtra("url")
        title = intent.getStringExtra("title")
        icon = intent.getIntExtra("icon", 0)
        needSuffix = intent.getBooleanExtra("needSuffix", true)
        miniProgramId = intent.getStringExtra("miniProgramId")
        if (needSuffix) {
            WebView.setDataDirectorySuffix(processName)
        }
        Log.i(TAG, "initView: url:$url")
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.actionBar.setPadding(
            0,
            cn.xiaobaihome.xiaobaihelper.helper.extens.getStatusBarHeight(this), 0, 0
        )
        binding.actionBar.navigationView.hide()
        binding.actionBar.titleView.hide()
        MiniProgramManager.activities.add(this)
        if (!title.isNullOrBlank() && icon > 0) {
            setTaskDescription(ActivityManager.TaskDescription(title, icon))
        }
        loadUrl()
        initWebView()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        url = intent?.getStringExtra("url")
        title = intent?.getStringExtra("title")
        icon = intent?.getIntExtra("icon", 0) ?: 0
        MiniProgramManager.activities.add(this)
        if (!title.isNullOrBlank() && icon > 0) {
            setTaskDescription(ActivityManager.TaskDescription(title, icon))
        }
        loadUrl()
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
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        settings.domStorageEnabled = true//开启DOM
        settings.defaultTextEncodingName = "utf-8"
        settings.allowFileAccess = true
        settings.allowContentAccess = true
        settings.allowUniversalAccessFromFileURLs = true
        //开启调试模式
        WebView.setWebContentsDebuggingEnabled(true)
        binding.webViewActivityWebView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                Log.i("openUrl", "shouldOverrideUrlLoading: " + request.url)
                return super.shouldOverrideUrlLoading(view, request)
            }

        }
        binding.webViewActivityWebView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                binding.actionBar.setTitle(title)
                super.onReceivedTitle(view, title)
            }
        }
    }

    private fun loadUrl() {
        if (!miniProgramId.isNullOrBlank()) {
            val jsonFileName = "miniprogram/$miniProgramId/app.json"
            val configJson = getJson(jsonFileName, this)
            if (configJson.isNotBlank()) {
                try {
                    val config: AppConfig = Gson().fromJson(configJson, AppConfig::class.java)
                    if (config.pages.isNotEmpty()) {
                        val homePage = basePath + miniProgramId + "/" + config.pages[0] + ".html"
                        binding.webViewActivityWebView.loadUrl(homePage)
                        if (config.tabBar.list.isNotEmpty()) {
                            binding.tabBar.show()
                            binding.tabBar.setBackgroundColor(if (config.tabBar.backgroundColor > 0) config.tabBar.backgroundColor else Color.WHITE)
                            binding.tabBar.itemTextColor =
                                ColorStateList.valueOf(if (config.tabBar.color > 0) config.tabBar.color else Color.BLACK)
                            val menu = binding.tabBar.menu
                            for ((index, item) in config.tabBar.list.withIndex()) {
                                menu.add(0, index, index, item.text)
                            }
                        } else {
                            binding.tabBar.hide()
                        }
                    } else {
                        toast("pages没有页面")
                    }
                } catch (e: Exception) {
                    toast("配置文件app.json异常")
                }
            } else {
                val homePage = "$basePath$miniProgramId/index.html"
                binding.webViewActivityWebView.loadUrl(homePage)
            }
        }
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.push_none, R.anim.slide_bottom_out)
    }

    companion object {
        private const val TAG = "WebViewActivity"
    }

}
