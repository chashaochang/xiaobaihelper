package cn.xiaobaihome.xiaobaihelper.startup

import android.content.Context
import androidx.startup.Initializer
import cn.xiaobaihome.xiaobaihelper.api.OkHttpManager

class OkHttpInitializer: Initializer<OkHttpManager> {

    override fun create(context: Context): OkHttpManager {
        return OkHttpManager.instance
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

}