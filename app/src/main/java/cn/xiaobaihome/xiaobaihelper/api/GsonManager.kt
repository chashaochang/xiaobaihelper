package cn.xiaobaihome.xiaobaihelper.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class GsonManager private constructor(){

    companion object{
        val instance : GsonManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            GsonManager()
        }
    }

    val gson: Gson by lazy {
        GsonBuilder().serializeNulls().disableHtmlEscaping().create()
    }

}