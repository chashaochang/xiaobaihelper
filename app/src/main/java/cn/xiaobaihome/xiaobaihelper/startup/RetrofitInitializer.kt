package cn.xiaobaihome.xiaobaihelper.startup

import android.content.Context
import androidx.startup.Initializer
import cn.xiaobaihome.xiaobaihelper.api.RetrofitManager

class RetrofitInitializer : Initializer<RetrofitManager> {

    override fun create(context: Context): RetrofitManager {
        return RetrofitManager.instance
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(GsonInitializer::class.java, OkHttpInitializer::class.java)
    }

}