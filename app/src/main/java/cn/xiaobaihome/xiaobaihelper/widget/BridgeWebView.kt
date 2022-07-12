package cn.xiaobaihome.xiaobaihelper.widget

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.webkit.*
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun BridgeWebView(
    modifier: Modifier = Modifier,
    url: String,
    onBack: (webView: WebView?) -> Unit,
    onProgressChange: (progress: Int) -> Unit = {},
    progressMarginTop: Dp = 0.dp,
    isShowProgress: Boolean = true,
//    onSetHeadBar:(SetHeadBarActionParam) -> Unit = {},
    onScroll: (Int) -> Unit = {}
) {
//    val context = LocalContext.current
//    var webView: MGWebView? = null
//    val gson = GsonManager.instance.gson
//    var progress by remember{
//        mutableStateOf(0)
//    }
//    var callNativeActionMGBridgeHandler: MGBridgeHandler?
//    var callNativeSettingMGBridgeHandler: MGBridgeHandler?
//    //动作管理器
//    var actionManager: ActionManager? = null
//    val callBackActions = Hashtable<String, CallBackAction>()
//    val chromeClientListener: ChromeClientListener = object : ChromeClientListener {
//        override fun openWindow(url: String) {
//
//            when {
//                url.startsWith(MGBridgeUtil.MANGO_RETURN_DATA) -> {
//                    webView?.handlerReturnData(url)
//                }
//                url.startsWith(MGBridgeUtil.MANGO_OVERIDE_SCHEMA) -> {
//                    webView?.flushMessageQueue()
//                }
//                else -> {
//                    RouterUtils.route(url, RouterUtils.ROUTE_MODE_WEB, context)
//                }
//            }
//        }
//
//        override fun onProgressChanged(newProgress: Int) {
//            onProgressChange(newProgress)
//            progress = newProgress
//        }
//
//        override fun onReceivedTitle(webView: WebView?, title: String?) {
//
//        }
//    }
//    val fileChooserListener: FileChooserListener = object : FileChooserListener {
//        override fun openFileChooser(uploadMsg: ValueCallback<Uri>) {}
//        override fun onShowFileChooser(
//            valueCallback: ValueCallback<Array<Uri>>,
//            fileChooserParams: WebChromeClient.FileChooserParams
//        ) {
//            //openFileChooserActivity(fileChooserParams.acceptTypes[0])
//        }
//    }
//    val webViewChromeClient = MGBaseBridgeChromeClient(chromeClientListener, fileChooserListener)

    val coroutineScope = rememberCoroutineScope()
//    Box {
//        AndroidView(modifier = modifier, factory = { ctx ->
//            MGWebView(ctx).apply {
//                this.webChromeClient = webViewChromeClient
//                webView = this
//                WebView.setWebContentsDebuggingEnabled(true)
//                //注意顺序，先定义 BridgeHandler 然后注册才能够生效
//                callNativeActionMGBridgeHandler =
//                    MGBridgeHandler { data: String?, function: MGCallBackFunction? ->
//                        try {
//                            actionManager?.exec(data, function)
//                        } catch (e: Exception) {
//                            //Log.d(MGWebViewActivity.TAG, "handler: $e")
//                        }
//                    }
//                callNativeSettingMGBridgeHandler =
//                    MGBridgeHandler { data: String?, function: MGCallBackFunction? ->
//                        try {
//                            actionManager!!.exec(data, function)
//                        } catch (e: Exception) {
//                            e.printStackTrace()
//                        }
//                    }
//                //处理js请求的所有的动作处理句柄
//                registerHandler("callNativeAction", callNativeActionMGBridgeHandler)
//                registerHandler("callNativeWebviewSetting", callNativeSettingMGBridgeHandler)
//                actionManager = ActionManager(gson)
//                //编辑房产
//                val houseEditAction = HouseEditAction(context, gson, actionManager)
//                actionManager!!.register("openHousePropertyEditAction", houseEditAction)
//                callBackActions["openHousePropertyEditAction"] = houseEditAction
//                actionManager?.register("openBaiduMapWithoutParamAction", object :IAction{
//                    override fun action(
//                        data: String?,
//                        requestData: String?,
//                        function: MGCallBackFunction?
//                    ) {
//                        val param = gson.fromJson(
//                            data,
//                            OpenBaiduMapPageActionParam::class.java
//                        )
//                        context.startActivity(
//                            Intent(
//                                context,
//                                MapFindHouseActivity::class.java
//                            ).putExtra("type", param.type)
//                        )
//                    }
//
//                    override fun response(function: MGCallBackFunction?, response: String?) {
//
//                    }
//
//                })
//                actionManager?.register("openAlbumAction", OpenAlbumAction(context, gson))
//                actionManager?.register(
//                    "surroundingBuildingAction",
//                    OpenSurroundingBuildingAction(context, gson)
//                )
//                actionManager?.register("shareAction", MGShareAction(context, gson))
//                //关闭页面
//                actionManager?.register("goBackNativeAction", object : IAction {
//                    @Throws(ParamParserException::class)
//                    override fun action(
//                        data: String,
//                        requestData: String,
//                        function: MGCallBackFunction
//                    ) {
//                        if (context is Activity) {
//                            context.finish()
//                        }
//                    }
//
//                    override fun response(function: MGCallBackFunction, response: String) {}
//                })
//                val setHeadBarAction =
//                    SetHeadBarAction(
//                        context,
//                        gson
//                    )
//                setHeadBarAction.setListener { param: SetHeadBarActionParam ->
//                    onSetHeadBar(param)
//                }
//
//                val extraHeaders: MutableMap<String, String>
//                extraHeaders = HashMap()
//                extraHeaders[NetworkConfig.MGSIGNATURE] = CommonData.getToken()
//                extraHeaders[NetworkConfig.DB_SOURCE] = "4"
//                extraHeaders[NetworkConfig.APP_ID_NAME] = NetworkConfig.APP_ID_VALUE
//                extraHeaders[NetworkConfig.MGDEVICEID] = Util.getUuid(context)
//                extraHeaders["User-Agent"] = getUserAgent(context)
//                loadUrl(url, extraHeaders)
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    setOnScrollChangeListener { _, _, i2, _, _ ->
//                        onScroll(i2)
//                    }
//                }
//
//            }
//        })
//        if(isShowProgress && progress < 100){
//            Spacer(modifier = Modifier.height(progressMarginTop))
//            LinearProgressIndicator(
//                modifier = Modifier.fillMaxWidth(),
//                progress = progress.toFloat() / 100,
//                color = Color.primary
//            )
//        }
    }
//    BackHandler {
//        coroutineScope.launch {
//            //自行控制点击了返回按键之后，关闭页面还是返回上一级网页
//            onBack(webView)
//        }
//    }
//}