package cn.xiaobaihome.xiaobaihelper.api

import android.annotation.SuppressLint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class OkHttpManager private constructor() {

    companion object {
        val instance: OkHttpManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            OkHttpManager().apply {
                init()
            }
        }
    }

    lateinit var okHttpClient: OkHttpClient

    private fun init() {
        val xtm = @SuppressLint("CustomX509TrustManager")
        object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate?> {
                return arrayOfNulls(0)
            }
        }
        val sslContext: SSLContext
        try {
            sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, arrayOf<TrustManager>(xtm), SecureRandom())
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: KeyManagementException) {
            throw RuntimeException(e)
        }
        val builder = OkHttpClient.Builder()
        builder.hostnameVerifier { _, _ ->
            true
        }
        //builder.cookieJar(CookieJar())
        builder.sslSocketFactory(sslContext.socketFactory, xtm)
        //builder.addInterceptor()
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLogger())
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addNetworkInterceptor(httpLoggingInterceptor)
        builder.addNetworkInterceptor(NetInterceptor())
        builder.callTimeout(20, TimeUnit.SECONDS)
        builder.connectTimeout(20, TimeUnit.SECONDS)
        builder.readTimeout(20, TimeUnit.SECONDS)
        builder.followRedirects(false)//禁止重定向，防止openwrt登录后重定向
        okHttpClient = builder.build()
    }
}