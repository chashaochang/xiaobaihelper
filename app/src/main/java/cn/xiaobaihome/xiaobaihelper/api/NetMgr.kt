package cn.xiaobaihome.xiaobaihelper.api


import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * 页面描述：网络管理类
 */

object NetMgr {
    private val providerMap = HashMap<String, NetProvider>()
    private val retrofitMap = HashMap<String, Retrofit>()
    private val clientMap = HashMap<String, OkHttpClient>()

    @JvmOverloads
    fun getRetrofit(baseUrl: String, netProvider: NetProvider? = null): Retrofit {
        var provider = netProvider
        if (empty(baseUrl)) {
            throw IllegalStateException("baseUrl can not be null")
        }
        if (retrofitMap[baseUrl] != null) {
            return retrofitMap[baseUrl]!!
        }

        if (provider == null) {
            provider = providerMap[baseUrl]
            if (provider == null) {
                provider = commonProvider
            }
        }
        checkProvider(provider)

        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()

        val builder = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getClient(baseUrl, provider!!))
                .addConverterFactory(GsonConverterFactory.create(gson))

        val retrofit = builder.build()
        retrofitMap[baseUrl] = retrofit
        providerMap[baseUrl] = provider

        return retrofit
    }

    private fun empty(baseUrl: String?): Boolean {
        return baseUrl == null || baseUrl.isEmpty()
    }

    private fun getClient(baseUrl: String, provider: NetProvider): OkHttpClient {
        if (empty(baseUrl)) {
            throw IllegalStateException("baseUrl can not be null")
        }
        if (clientMap[baseUrl] != null) {
            return clientMap[baseUrl]!!
        }

        checkProvider(provider)

        val builder = OkHttpClient.Builder()

        builder.connectTimeout(if (provider.configConnectTimeoutSecs() != 0L)
            provider.configConnectTimeoutSecs()
        else
            connectTimeoutMills, TimeUnit.SECONDS)
        builder.readTimeout(if (provider.configReadTimeoutSecs() != 0L)
            provider.configReadTimeoutSecs()
        else
            readTimeoutMills, TimeUnit.SECONDS)

        builder.writeTimeout(if (provider.configWriteTimeoutSecs() != 0L)
            provider.configReadTimeoutSecs()
        else
            readTimeoutMills, TimeUnit.SECONDS)
        provider.configHttps(builder)

        val handler = provider.configHandler()
        builder.addInterceptor(NetInterceptor(handler))

        val interceptors = provider.configInterceptors()
        if (!empty(interceptors)) {
            for (interceptor in interceptors!!) {
                builder.addInterceptor(interceptor)
            }
        }

        if (provider.configLogEnable()) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }

        val client = builder.build()
        clientMap[baseUrl] = client
        providerMap[baseUrl] = provider

        return client
    }

    private fun empty(interceptors: Array<Interceptor>?): Boolean {
        return interceptors == null || interceptors.isEmpty()
    }

    private fun checkProvider(provider: NetProvider?) {
        if (provider == null) {
            throw IllegalStateException("must register provider first")
        }
    }

    private const val connectTimeoutMills = 10 * 1000L
    private const val readTimeoutMills = 10 * 1000L
    private var commonProvider: NetProvider? = null

    operator fun <S> get(baseUrl: String, service: Class<S>): S {
        return getRetrofit(baseUrl).create(service)
    }

    fun registerProvider(provider: NetProvider) {
        commonProvider = provider
    }


}
