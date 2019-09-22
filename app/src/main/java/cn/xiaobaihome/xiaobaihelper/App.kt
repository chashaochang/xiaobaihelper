package cn.xiaobaihome.xiaobaihelper

import android.app.Application
import android.util.Log
import cn.xiaobaihome.xiaobaihelper.di.appModule
import cn.xiaobaihome.xiaobaihelper.helper.network.NetMgr
import cn.xiaobaihome.xiaobaihelper.mvvm.model.remote.BaseNetProvider
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.tencent.smtt.sdk.QbSdk
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        NetMgr.registerProvider(BaseNetProvider(this))
        startKoin(this, appModule)
        //x5内核初始化接口
        QbSdk.initX5Environment(this, object : QbSdk.PreInitCallback {
            override fun onCoreInitFinished() {

            }

            override fun onViewInitFinished(p0: Boolean) {
                Log.i("App","X5 init:$p0")
            }

        })
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(applicationContext)
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL)
    }
}