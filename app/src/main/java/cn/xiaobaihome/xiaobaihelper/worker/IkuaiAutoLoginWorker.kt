package cn.xiaobaihome.xiaobaihelper.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import cn.xiaobaihome.xiaobaihelper.api.IKuaiApiService
import cn.xiaobaihome.xiaobaihelper.bean.IkuaiLoginBean
import cn.xiaobaihome.xiaobaihelper.helper.CacheUtil
import cn.xiaobaihome.xiaobaihelper.helper.md5
import kotlinx.coroutines.flow.flow

class IkuaiAutoLoginWorker(appContext: Context,workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val ikuaiApiService = IKuaiApiService.create()
        val username = CacheUtil.get(CacheUtil.IKUAI_USERNAME)
        val pwd = CacheUtil.get(CacheUtil.IKUAI_PWD)
        if(username.isNullOrEmpty() || pwd.isNullOrEmpty()) {
            return Result.failure()
        }
        val password = md5(pwd)
        flow {
            emit(ikuaiApiService.login(
                IkuaiLoginBean(username, password, password, true)
            ))
        }.collect{

        }
        return Result.success()
    }

}