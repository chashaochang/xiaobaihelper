package cn.xiaobaihome.xiaobaihelper.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import cn.xiaobaihome.xiaobaihelper.api.OpenWrtApiService
import cn.xiaobaihome.xiaobaihelper.helper.CacheUtil
import kotlinx.coroutines.flow.flow

class OpenWrtAutoLoginWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val openWrtApiService = OpenWrtApiService.create()
        val username = CacheUtil.get(CacheUtil.OPENWRT_USERNAME)
        val password = CacheUtil.get(CacheUtil.OPENWRT_PWD)
        if(username.isNullOrEmpty() || password.isNullOrEmpty()) {
            return Result.failure()
        }
        flow {
            emit(openWrtApiService.login(username, password))
        }.collect{

        }
        return Result.success()
    }

}