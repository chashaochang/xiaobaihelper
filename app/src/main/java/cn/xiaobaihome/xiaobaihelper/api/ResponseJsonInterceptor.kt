package cn.xiaobaihome.xiaobaihelper.api

import cn.xiaobaihome.xiaobaihelper.helper.CacheUtil
import okhttp3.Interceptor
import okhttp3.Response

class ResponseJsonInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val type = request.headers["type"]
        if (type != null && type == "ikuai") {//如果是ikuai的请求，携带cookie
            CacheUtil.get(CacheUtil.IKUAI_COOKIE)
                ?.let {
                    request.newBuilder().addHeader("Cookie", it).build()
                }
        }
        val response = chain.proceed(request)
        if (type != null && type == "ikuai") {//如果是ikuai的登录，保存返回的cookie
            if (request.url.toUrl().path.contains("login")) {
                response.headers["set-cookie"]?.let {
                    CacheUtil.set(CacheUtil.IKUAI_COOKIE, it)
                }
            }
        }
        return response
    }
}