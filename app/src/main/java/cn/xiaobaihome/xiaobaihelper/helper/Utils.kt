package cn.xiaobaihome.xiaobaihelper.helper

import android.content.Context
import android.content.Context.MODE_PRIVATE

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