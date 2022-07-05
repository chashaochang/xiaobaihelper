package cn.xiaobaihome.xiaobaihelper.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


/**
 * 页面描述：网络拦截
 */

interface RequestHandler {

    fun onBeforeRequest(request: Request, chain: Interceptor.Chain): Request

    @Throws(ApiException::class)
    fun onAfterRequest(response: Response, chain: Interceptor.Chain): Response

}
