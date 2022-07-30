package cn.xiaobaihome.xiaobaihelper.api

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import cn.xiaobaihome.xiaobaihelper.helper.AppData
import cn.xiaobaihome.xiaobaihelper.helper.CacheUtil
import cn.xiaobaihome.xiaobaihelper.worker.OpenWrtAutoLoginWorker
import okhttp3.Interceptor
import okhttp3.Response

class NetInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val type = request.headers["type"]
        if (type != null && type == "ikuai") {//如果是ikuai的请求，携带cookie
            CacheUtil.get(CacheUtil.IKUAI_COOKIE)
                ?.let {
                    request = request.newBuilder()
                        .addHeader("Cookie", it)
                        .build()
                }
        } else if (type != null && type == "openwrt") {
            CacheUtil.get(CacheUtil.OPENWRT_COOKIE)
                ?.let {
                    request = request.newBuilder()
                        .addHeader("Cookie", it)
                        .build()
                }
        }
        val response = chain.proceed(request)
        if (type != null && type == "ikuai") {//如果是ikuai的登录，保存返回的cookie
            if (request.url.toUrl().path.contains("login")) {
                response.headers["set-cookie"]?.let {
                    CacheUtil.set(CacheUtil.IKUAI_COOKIE, it)
                }
            }
        } else if (type != null && type == "openwrt") {
            if (request.url.toUrl().path.contains("cgi-bin/luci") && response.code == 302) {
                response.headers["set-cookie"]?.let {
                    CacheUtil.set(CacheUtil.OPENWRT_COOKIE, it)
                    AppData.isOpenWrtLogin.value = true
                }
            }
            //登陆过期
            if(response.code == 403){
                CacheUtil.set(CacheUtil.OPENWRT_COOKIE, "")
            }
        }
        return response
    }
}