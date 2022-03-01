package cn.xiaobaihome.xiaobaihelper.api

import cn.xiaobaihome.xiaobaihelper.BuildConfig
import okhttp3.*
import java.io.IOException

/**
 * 页面描述：BaseNetProvider
 */

class BaseNetProvider : NetProvider {

    override fun configInterceptors(): Array<Interceptor>? {
        return null
    }

    override fun configHttps(builder: OkHttpClient.Builder) {

    }

    override fun configHandler(): RequestHandler {

        return HeaderHandler()
    }

    override fun configConnectTimeoutSecs(): Long {
        return CONNECT_TIME_OUT
    }

    override fun configReadTimeoutSecs(): Long {
        return READ_TIME_OUT
    }

    override fun configWriteTimeoutSecs(): Long {
        return WRITE_TIME_OUT
    }

    override fun configLogEnable(): Boolean {
        return BuildConfig.DEBUG
    }

    inner class HeaderHandler : RequestHandler {

        override fun onBeforeRequest(request: Request, chain: Interceptor.Chain): Request {
            return request
        }

        @Throws(IOException::class)
        override fun onAfterRequest(response: Response, chain: Interceptor.Chain): Response {
            var e: ApiException? = null
            when {
                401 == response.code -> e = ApiException("登录已过期,请重新登录!", response.code)
                403 == response.code -> e = ApiException("禁止访问!", response.code)
                404 == response.code -> e = ApiException("接口不存在", response.code)
                503 == response.code -> e = ApiException("服务器维护中!", response.code)
                response.code > 300 -> {
                    val message = response.body!!.string()
                    e = if (Utils.check(message)) {
                        ApiException("服务器内部错误!", response.code)
                    } else {
                        ApiException(message, response.code)
                    }
                }
            }
            if (!Utils.check(e)) {
                throw e!!
            }
            return response
        }
    }

    companion object {

        const val CONNECT_TIME_OUT: Long = 20
        const val READ_TIME_OUT: Long = 180
        const val WRITE_TIME_OUT: Long = 30
    }
}
