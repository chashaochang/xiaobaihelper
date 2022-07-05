package cn.xiaobaihome.xiaobaihelper.startup

import android.content.Context
import androidx.startup.Initializer
import cn.xiaobaihome.xiaobaihelper.api.GsonManager
import cn.xiaobaihome.xiaobaihelper.api.OkHttpManager

class GsonInitializer: Initializer<GsonManager> {

    override fun create(context: Context): GsonManager {
        return GsonManager.instance
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

}