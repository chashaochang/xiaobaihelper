package cn.xiaobaihome.xiaobaihelper.helper

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import cn.xiaobaihome.xiaobaihelper.bean.VideoHistoryItem
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.Buffer
import okio.EOFException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.math.RoundingMode
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.charset.UnsupportedCharsetException
import java.security.MessageDigest
import java.text.DecimalFormat
import kotlin.math.pow


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
        hex.append(str.substring(str.length - 2))
    }
    return hex.toString()
}

fun parseTime(time: Int): String {
    val seconds = time % 60
    val minutes = time / 60 % 60
    val hours = time / 60 / 60
    val optime = "${hours}:${minutes}:${seconds}"
    val items = optime.split(":") as MutableList
    val days = items[0].toInt() / 24
    items[0] = (items[0].toInt() % 24).toString()
//    items[1] = items[1].toString().padLeft(2, "0")
//    items[2] = items[2].toString().padLeft(2, "0")
    return "${if (days > 0) "${days}天" else ""}${items[0]}时${items[1]}分"
}

fun parseTimeOp(time: Int): String {
    val seconds = time % 60
    val minutes = time / 60 % 60
    val hours = time / 60 / 60
    val optime = "${hours}:${minutes}:${seconds}"
    val items = optime.split(":") as MutableList
    val days = items[0].toInt() / 24
    items[0] = (items[0].toInt() % 24).toString()
//    items[1] = items[1].toString().padLeft(2, "0")
//    items[2] = items[2].toString().padLeft(2, "0")
    return "${if (days > 0) "${days}d " else ""}${items[0]}h ${items[1]}m ${items[2]}s"
}

fun formatSize(size: Long, unit: Double = 1024.0): String {
    val format = DecimalFormat("0.##")
    format.roundingMode = RoundingMode.FLOOR
    return if (size < unit) {
        "${size}B";
    } else if (size < unit.pow(2.0)) {
        "${format.format(size / unit)} KB";
    } else if (size < unit.pow(3.0)) {
        "${format.format(size / unit.pow(2.0))} MB";
    } else if (size < unit.pow(4.0)) {
        "${format.format(size / unit.pow(3.0))} GB";
    } else {
        "${format.format(size / unit.pow(4.0))} TB";
    }
}

/**
 * 解析 ResponseBody
 * @param responseBody -
 * @return 解析结果
 */
@Throws(Exception::class)
fun getResponseBody(responseBody: ResponseBody): String? {
    val source = responseBody.source()
    source.request(Long.MAX_VALUE)
    val buffer: Buffer = source.buffer
    var charset: Charset = StandardCharsets.UTF_8
    val contentType: MediaType? = responseBody.contentType()
    if (contentType != null) {
        try {
            charset = contentType.charset(StandardCharsets.UTF_8)!!
        } catch (e: UnsupportedCharsetException) {
            //DebugLog.e("将http数据写入流异常,异常原因：" + Arrays.toString(e.getStackTrace()))
        }
    }
    if (!isPlaintext(buffer)) {
        return null
    }
    if (responseBody.contentLength() != 0L) {
        return buffer.clone().readString(charset)
    }
    return null
}


@Throws(EOFException::class)
private fun isPlaintext(buffer: Buffer): Boolean {
    return try {
        val prefix = Buffer()
        val byteCount: Long = if (buffer.size < 64) buffer.size else 64
        buffer.copyTo(prefix, 0, byteCount)
        for (i in 0..15) {
            if (prefix.exhausted()) {
                break
            }
            val codePoint: Int = prefix.readUtf8CodePoint()
            if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                return false
            }
        }
        true
    } catch (e: EOFException) {
        false
    }
}
