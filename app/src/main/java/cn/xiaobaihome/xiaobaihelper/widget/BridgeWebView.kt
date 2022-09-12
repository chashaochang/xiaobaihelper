package cn.xiaobaihome.xiaobaihelper.widget


import android.webkit.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import java.util.*

@Composable
fun BridgeWebView(
    modifier: Modifier = Modifier,
    url: String,
    onBack: (webView: WebView?) -> Unit,
    onProgressChange: (progress: Int) -> Unit = {},
    progressMarginTop: Dp = 0.dp,
    isShowProgress: Boolean = true,
) {
    val webViewState = rememberWebViewState(url = url)
    Box {
        WebView(state = webViewState, onCreated = {
//                it.webChromeClient = webViewChromeClient
                WebView.setWebContentsDebuggingEnabled(true)
        }, captureBackPresses= false)//禁止webView捕获返回事件
//        if(isShowProgress && progress < 100){
//            Spacer(modifier = Modifier.height(progressMarginTop))
//            LinearProgressIndicator(
//                modifier = Modifier.fillMaxWidth(),
//                progress = progress.toFloat() / 100,
//                color = Color.Green
//            )
//        }
    }
}