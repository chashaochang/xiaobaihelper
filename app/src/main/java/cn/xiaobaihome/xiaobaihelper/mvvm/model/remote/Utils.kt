package cn.xiaobaihome.xiaobaihelper.mvvm.model.remote

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.widget.Toast
import okhttp3.*
import java.io.IOException
import kotlin.concurrent.thread

/**
 * 页面描述：Utils
 *
 * Created by ditclear on 2017/9/26.
 */

internal object Utils {

    fun check(message: String?): Boolean = message.isNullOrEmpty()

    fun check(o: Any?): Boolean = o == null

    //实现下载apk
    fun downLoadNew(context: Context,downUrl:String): Long {

        val request = DownloadManager.Request(Uri.parse(downUrl))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "xiaobaihelper.apk")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        // 设置 Notification 信息
        request.setTitle("正在下载更新")
        request.setDescription("下载完成后请点击打开")
        request.setVisibleInDownloadsUi(true)
        request.allowScanningByMediaScanner()
        request.setMimeType("application/vnd.android.package-archive")

        // 实例化DownloadManager 对象
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        return downloadManager.enqueue(request)
    }

}
