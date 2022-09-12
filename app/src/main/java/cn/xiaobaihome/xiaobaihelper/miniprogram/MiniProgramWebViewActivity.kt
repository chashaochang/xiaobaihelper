package cn.xiaobaihome.xiaobaihelper.miniprogram

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.helper.getJson
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseActivity
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class MiniProgramWebViewActivity : BaseActivity() {

    class AppBrandUI0 : MiniProgramWebViewActivity()
    class AppBrandUI1 : MiniProgramWebViewActivity()
    class AppBrandUI2 : MiniProgramWebViewActivity()
    class AppBrandUI3 : MiniProgramWebViewActivity()
    class AppBrandUI4 : MiniProgramWebViewActivity()

    private val basePath = "file:///android_asset/miniprogram/"

    private var url: String = ""
    private var title: String? = null
    private var icon = 0
    private var needSuffix: Boolean = true
    private var miniProgramId: String? = null

    private val viewModel: MiniProgramViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val processName = Application.getProcessName()
        url = intent.getStringExtra("url") ?: ""
        title = intent.getStringExtra("title")
        icon = intent.getIntExtra("icon", 0)
        needSuffix = intent.getBooleanExtra("needSuffix", true)
        miniProgramId = intent.getStringExtra("miniProgramId")
        if (needSuffix) {
            WebView.setDataDirectorySuffix(processName)
        }
        if (!title.isNullOrBlank() && icon > 0) {
            setTaskDescription(ActivityManager.TaskDescription(title, icon))
        }
        MiniProgramManager.activityMiniPrograms.add(this)
        viewModel.url.value = url
        if (!miniProgramId.isNullOrBlank()) {
            val jsonFileName = "miniprogram/$miniProgramId/app.json"
            val configJson = getJson(jsonFileName, this)
            if (configJson.isNotBlank()) {
                try {
                    val config: AppConfig = Gson().fromJson(configJson, AppConfig::class.java)
                    if (config.pages.isNotEmpty()) {
                        viewModel.url.value =
                            basePath + miniProgramId + "/" + config.pages[0] + ".html"
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
                viewModel.url.value = "$basePath$miniProgramId/index.html"
            }
        }
        setContent {
            val urlState = viewModel.url.collectAsState()
            val webViewState = rememberWebViewState(url = urlState.value)
            val webChromeClient = object : AccompanistWebChromeClient() {

            }
            Scaffold(
                bottomBar = {
                    Column {
                        BottomAppBar() {

                        }
                        Spacer(modifier = Modifier.navigationBarsPadding())
                    }
                }
            ) { it ->
                WebView(modifier = Modifier.padding(it), state = webViewState,
                    chromeClient = webChromeClient,
                    onCreated = { webView ->
                        initWebView(webView)
                    })
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        url = intent?.getStringExtra("url") ?: ""
        title = intent?.getStringExtra("title")
        icon = intent?.getIntExtra("icon", 0) ?: 0
        MiniProgramManager.activityMiniPrograms.add(this)
        if (!title.isNullOrBlank() && icon > 0) {
            setTaskDescription(ActivityManager.TaskDescription(title, icon))
        }
        viewModel.url.value = url
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(webView: WebView) {
        val settings = webView.settings
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
        //开启调试模式
        WebView.setWebContentsDebuggingEnabled(true)
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.push_none, R.anim.slide_bottom_out)
    }

    companion object {
        private const val TAG = "WebViewActivity"
    }

}
