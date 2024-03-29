package cn.xiaobaihome.xiaobaihelper

import android.app.Application
import cn.xiaobaihome.xiaobaihelper.helper.CacheUtil
import cn.xiaobaihome.xiaobaihelper.helper.getAppVersionName
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //x5内核初始化接口
//        QbSdk.initX5Environment(this, object : QbSdk.PreInitCallback {
//            override fun onCoreInitFinished() {
//
//            }
//
//            override fun onViewInitFinished(p0: Boolean) {
//                Log.i("App", "X5 init:$p0")
//            }
//
//        })
//        QbSdk.setTbsListener(object : TbsListener {
//            override fun onInstallFinish(p0: Int) {
//                Log.i("App", "X5 onInstallFinish:$p0")
//            }
//
//            override fun onDownloadFinish(p0: Int) {
//                Log.i("App", "X5 onDownloadFinish:$p0")
//            }
//
//            override fun onDownloadProgress(p0: Int) {
//                Log.i("App", "X5 onDownloadProgress:$p0")
//            }
//
//        })
//        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
//        SDKInitializer.initialize(applicationContext)
//        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
//        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
//        SDKInitializer.setCoordType(CoordType.BD09LL)
        CacheUtil.init(this)
        //存储当前版本号
        CacheUtil.set(CacheUtil.CACHE_APP_VERSION,getAppVersionName(this))
    }

}