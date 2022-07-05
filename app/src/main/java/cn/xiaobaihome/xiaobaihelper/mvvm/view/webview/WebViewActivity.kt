package cn.xiaobaihome.xiaobaihelper.mvvm.view.webview

import android.os.Bundle
import cn.xiaobaihome.xiaobaihelper.databinding.ActivityWebviewBinding
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewActivity : BaseActivity() {

    private lateinit var binding: ActivityWebviewBinding
    private var url: String? = null
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        url = intent.getStringExtra("url")
        title = intent.getStringExtra("title")
        binding.webViewActivityWebView.loadUrl(url ?: "")
    }
}