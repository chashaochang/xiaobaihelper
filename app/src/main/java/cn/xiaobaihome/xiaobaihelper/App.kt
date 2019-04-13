package cn.xiaobaihome.xiaobaihelper

import android.app.Application
import cn.xiaobaihome.xiaobaihelper.di.appModule
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, appModule)
    }
}