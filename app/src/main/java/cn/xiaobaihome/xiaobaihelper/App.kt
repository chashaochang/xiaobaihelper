package cn.xiaobaihome.xiaobaihelper

import android.app.Application
import cn.xiaobaihome.xiaobaihelper.di.appModule
import cn.xiaobaihome.xiaobaihelper.helper.network.NetMgr
import cn.xiaobaihome.xiaobaihelper.mvvm.model.remote.BaseNetProvider
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        NetMgr.registerProvider(BaseNetProvider(this))
        startKoin(this, appModule)
    }
}