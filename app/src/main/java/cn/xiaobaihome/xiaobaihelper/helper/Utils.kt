package cn.xiaobaihome.xiaobaihelper.helper

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import cn.xiaobaihome.xiaobaihelper.bean.VideoHistoryItem
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.security.MessageDigest

fun addVideoHistory(context: Context, videoHistoryItem: VideoHistoryItem) {
//    val historyListStr = getData(context, "video_history")
//    val historyList: MutableList<VideoHistoryItem> = if (historyListStr.isEmpty()) {
//        ArrayList()
//    } else {
//        Gson().fromJson(historyListStr, object : TypeToken<MutableList<VideoHistoryItem>>() {}.type)
//    }
//    val iterator = historyList.iterator()
//    while (iterator.hasNext()) {
//        val historyItem = iterator.next()
//        if (historyItem.coverImg == videoHistoryItem.coverImg) {
//            iterator.remove()
//        }
//    }
//    if (historyList.size > 19) {//已经有20个,移除第一个
//        historyList.removeAt(0)
//    }
//    historyList.add(videoHistoryItem)
    //saveData(context, "video_history", Gson().toJson(historyList))
}

fun getJson(fileName: String, context: Context): String {
    //将json数据变成字符串
    val stringBuilder = StringBuilder()
    try {
        //获取assets资源管理器
        val assetManager = context.assets
        //通过管理器打开文件并读取
        val bf = BufferedReader(
            InputStreamReader(
                assetManager.open(fileName)
            )
        )
        var line: String?
        while (bf.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return stringBuilder.toString()
}

fun getUA(): String {
    if ("" == APP_UA) {
        APP_UA =
            USER_AGENT + getAppVersionName() + "|" + getPhoneInfo()// + "|" + UserUtils.getUserId()
    }
    return APP_UA
}

fun getPhoneInfo(): String {
    return Build.BRAND.toString() + " " + Build.MODEL + "|" + Build.VERSION.RELEASE
}

fun getAppVersionName(context: Context): String {
    try {
        return context.packageManager.getPackageInfo(context.packageName, 0).versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return ""
}

fun getAppVersionName(): String {
    return CacheUtil.get(CacheUtil.CACHE_APP_VERSION) ?: ""
}

/** md5加密 */
fun md5(content: String): String {
    val hash = MessageDigest.getInstance("MD5").digest(content.toByteArray())
    val hex = StringBuilder(hash.size * 2)
    for (b in hash) {
        var str = Integer.toHexString(b.toInt())
        if (b < 0x10) {
            str = "0$str"
        }
        hex.append(str.substring(str.length -2))
    }
    return hex.toString()
}