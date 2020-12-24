package cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.video

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.webkit.JavascriptInterface
import cn.xiaobaihome.xiaobaihelper.databinding.ActivityVideoWebviewBinding
import cn.xiaobaihome.xiaobaihelper.entity.VideoHistoryItem
import cn.xiaobaihome.xiaobaihelper.helper.addVideoHistory
import cn.xiaobaihome.xiaobaihelper.helper.extens.toast
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseActivity
import cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.play.PlayActivity
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import java.util.*

class VideoWebViewActivity : BaseActivity() {

    private var url: String? = null
    private var playUrl: String? = null
    private var title: String? = null
    private var coverImg: String? = null
    private lateinit var binding : ActivityVideoWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityVideoWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        url = intent.getStringExtra("url")
        initWebView()
        loadUrl()
        binding.videoWebviewActivityPlayBtn.setOnClickListener {
            if (playUrl != null && playUrl!!.isNotEmpty()) {
                goToPlay()
            }
        }
        binding.videoWebviewActivityBackBtn.setOnClickListener {
            goBack()
        }
        binding.videoWebviewActivityCloseBtn.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        val settings = binding.videoWebviewActivityWebview.settings
        settings.loadWithOverviewMode = true
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.builtInZoomControls = true
        settings.displayZoomControls = true
        settings.supportZoom()
        //settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        settings.domStorageEnabled = true//开启DOM
        settings.defaultTextEncodingName = "utf-8"
        binding.videoWebviewActivityWebview.addJavascriptInterface(object : GetCoverImg {

            @JavascriptInterface
            override fun getCoverImg(imgUrl: String) {
                coverImg = imgUrl
            }

        }, "xbBridge")
        binding.videoWebviewActivityWebview.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val url = request.url.toString()
                if (request.url.scheme != null && request.url.scheme!!.contains("http")) {
                    view.loadUrl(url)
                    return false
                }
                return true
            }

            override fun onPageFinished(p0: WebView?, p1: String?) {
                super.onPageFinished(p0, p1)
                if (p1.toString().contains("m.iqiyi.com/v_")) {//爱奇艺
                    binding.videoWebviewActivityWebview.loadUrl("javascript:window.xbBridge.getCoverImg(document.getElementById(\"player-posterimg\").src)")
                } else if (p1.toString().contains("//m.v.qq.com")) {
                    binding.videoWebviewActivityWebview.loadUrl("javascript:window.xbBridge.getCoverImg(document.getElementsByClassName(\"poster_pic\")[0].src)")
                } else if (p1.toString().contains("//m.youku.com")) {
                    binding.videoWebviewActivityWebview.loadUrl("javascript:window.xbBridge.getCoverImg(document.getElementsByClassName(\"x-video-poster\")[0].firstChild.src)")
                } else if (p1.toString().contains("//m.bilibili.com")) {
                    binding.videoWebviewActivityWebview.loadUrl("javascript:window.xbBridge.getCoverImg(document.getElementsByClassName(\"player-mask relative\")[0].firstChild.src)")
                }
            }

        }
        binding.videoWebviewActivityWebview.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                binding.videoWebviewActivityTessss.text = view.url
                binding.videoWebviewActivityTitle.text = title
                this@VideoWebViewActivity.title = title
                if (binding.videoWebviewActivityWebview.canGoBack()) {
                    binding.videoWebviewActivityCloseBtn.visibility = View.VISIBLE
                } else {
                    binding.videoWebviewActivityCloseBtn.visibility = View.GONE
                }
                parseUrl(view.url)
                super.onReceivedTitle(view, title)
            }
        }
    }

    private fun parseUrl(url: String) {
        binding.videoWebviewActivityPlayBtn.visibility = View.GONE
        val txurlc = url.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val feurl = txurlc[1]
        if (feurl.length > 22) {
            val txurl = feurl.substring(0, 22)
            val vid = getValueByName(url, "vid")
            if (txurl.contains("//m.v.qq.com") && (txurl.contains("/cover/") || txurl.contains("play.html"))) {
                binding.videoWebviewActivityPlayBtn.visibility = View.VISIBLE
                val txdata2 = url.split("\\?".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                var cid = getValueByName(url, "cid")
                if (cid.isEmpty()) {
                    cid = txdata2[0].substring(txdata2[0].length - 20, txdata2[0].length - 5)
                }
                playUrl = if (vid.length == 11) {
                    "https://v.qq.com/x/cover/$cid/$vid.html"
                } else {
                    "https://v.qq.com/x/cover/$cid.html"
                }
            }
            //https://m.v.qq.com/x/m/play?cid=m441e3rjq9kwpsc&vid=n0033turevc&ref_pg=page_video_detail
            if (txurl.contains("//m.v.qq.com") && (txurl.contains("/x/m/") || txurl.contains("play?"))) {
                binding.videoWebviewActivityPlayBtn.visibility = View.VISIBLE
                val txdata2 = url.split("\\?".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                var cid = getValueByName(url, "cid")
                if (cid.isEmpty()) {
                    cid = txdata2[0].substring(txdata2[0].length - 20, txdata2[0].length - 5)
                }
                playUrl = if (vid.length == 11) {
                    "https://v.qq.com/x/cover/$cid/$vid.html"
                } else {
                    "https://v.qq.com/x/cover/$cid.html"
                }
            }
            if (txurl.contains("//m.iqiyi.com") && feurl.substring(0, 16) == "//m.iqiyi.com/v_") {
                binding.videoWebviewActivityPlayBtn.visibility = View.VISIBLE
                val ykdata = feurl.substring(13)
                playUrl = "https://www.iqiyi.com$ykdata"
            }
            if ((txurl.contains("//m.bilibili.com") && feurl.length > 30 && feurl.substring(0, 30).contains("m.bilibili.com/video")) ||
                    feurl.contains("//m.bilibili.com") && feurl.contains("/play/")) {
                binding.videoWebviewActivityPlayBtn.visibility = View.VISIBLE
                playUrl = url
                //goToPlay()
            }
            if (txurl.contains("//m.youku.com") && feurl.substring(0, 23) == "//m.youku.com/video/id_") {
                binding.videoWebviewActivityPlayBtn.visibility = View.VISIBLE
                playUrl = url
            }
            if (feurl.substring(0, 16) == "//m.iqiyi.com/v_"
                    || feurl.substring(0, 19) == "//v.qq.com/x/cover/"
                    || feurl.substring(0, 17) == "//m.tv.sohu.com/v"
                    || feurl.length > 23 && feurl.substring(0, 23) == "//m.film.sohu.com/album"
                    || feurl.substring(0, 14) == "//m.mgtv.com/b"
                    || feurl.substring(0, 21) == "//www.youku.com/video"
                    || feurl.substring(0, 22) == "//www.le.com/ptv/vplay"
                    || feurl.length > 30 && feurl.substring(0, 30) == "//www.wasu.cn/wap/play/show/id"
                    || feurl.length > 60 && feurl.substring(0, 60) == "//m.miguvideo.com/wap/resource/migu/miguH5/detail/detail.jsp") {
                //goToPlay();
            }
        }
    }

    private fun loadUrl() {
        // 加载Web地址
        if (url?.isNotEmpty()!!) {
            binding.videoWebviewActivityWebview.loadUrl(url)
        }
    }

    override fun onBackPressed() {
        goBack()
    }

    private fun goBack() {
        if (binding.videoWebviewActivityWebview.canGoBack()) {
            binding.videoWebviewActivityWebview.goBack()
        } else {
            finish()
        }
    }

    private fun goToPlay() {
        val intent = Intent(this, PlayActivity::class.java)
        intent.putExtra("url", playUrl)
        intent.putExtra("title", title)
        startActivity(intent)

        if (coverImg != null) {
            addHistory(title.toString(), playUrl.toString(), coverImg.toString())
        } else {
            toast("没找到封面图")
        }
    }

    private fun addHistory(title: String, url: String, coverImg: String) {
        addVideoHistory(this, VideoHistoryItem(title, url, coverImg, Date().time, ""))
    }

    private fun getValueByName(url: String, name: String): String {
        var result = ""
        val index = url.indexOf("?")
        val temp = url.substring(index + 1)
        val keyValue = temp.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (str in keyValue) {
            if (str.contains(name)) {
                result = str.replace("$name=", "")
                break
            }
        }
        return result
    }

    interface GetCoverImg {

        @JavascriptInterface
        fun getCoverImg(imgUrl: String)
    }
}