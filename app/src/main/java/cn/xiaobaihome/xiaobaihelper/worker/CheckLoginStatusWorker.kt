package cn.xiaobaihome.xiaobaihelper.worker

import android.content.Context
import androidx.work.*
import cn.xiaobaihome.xiaobaihelper.helper.AppData
import cn.xiaobaihome.xiaobaihelper.helper.CacheUtil

class CheckLoginStatusWorker(private val appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val opCookie = CacheUtil.get(CacheUtil.OPENWRT_COOKIE)
        val iKuaiCookie = CacheUtil.get(CacheUtil.IKUAI_COOKIE)
        if(AppData.isOpenWrtLogin.value && opCookie.isNullOrBlank()){//已登录cookie失效
            val workRequest = OneTimeWorkRequestBuilder<OpenWrtAutoLoginWorker>()
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .build()
            WorkManager.getInstance(appContext)
                .enqueue(workRequest)
        }
        if(AppData.isIKuaiLogin.value && iKuaiCookie.isNullOrBlank()){//已登录cookie失效
            val workRequest = OneTimeWorkRequestBuilder<IkuaiAutoLoginWorker>()
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .build()
            WorkManager.getInstance(appContext)
                .enqueue(workRequest)
        }
        return Result.success()
    }

}