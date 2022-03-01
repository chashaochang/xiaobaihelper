package cn.xiaobaihome.xiaobaihelper.helper

import android.content.Context
import android.content.Context.MODE_PRIVATE
import cn.xiaobaihome.xiaobaihelper.entity.VideoHistoryItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*


//存储key对应的数据
fun saveData(context: Context, key: String, info: String) {
    val sharedPreferences = context.getSharedPreferences(key, MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString(key, info)
    editor.apply()
}

//取key对应的数据
fun getData(context: Context, key: String): String {
    val result = context.getSharedPreferences(key, MODE_PRIVATE).getString(key, "")
    return if (result!!.isEmpty()) {
        ""
    } else {
        result
    }
}

//清空缓存对应key的数据
fun clearData(context: Context, key: String) {
    context.getSharedPreferences(key, MODE_PRIVATE).edit().clear().apply()
}

fun addVideoHistory(context: Context, videoHistoryItem: VideoHistoryItem) {
    val historyListStr = getData(context, "video_history")
    val historyList: MutableList<VideoHistoryItem> = if (historyListStr.isEmpty()) {
        ArrayList()
    } else {
        Gson().fromJson(historyListStr, object : TypeToken<MutableList<VideoHistoryItem>>() {}.type)
    }
    val iterator = historyList.iterator()
    while (iterator.hasNext()){
        val historyItem = iterator.next()
        if (historyItem.coverImg == videoHistoryItem.coverImg) {
            iterator.remove()
        }
    }
    if (historyList.size > 19) {//已经有20个,移除第一个
        historyList.removeAt(0)
    }
    historyList.add(videoHistoryItem)
    saveData(context, "video_history", Gson().toJson(historyList))
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