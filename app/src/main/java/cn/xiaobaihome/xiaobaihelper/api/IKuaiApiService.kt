package cn.xiaobaihome.xiaobaihelper.api

import cn.xiaobaihome.xiaobaihelper.mvvm.model.MinerStatus
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface IKuaiApiService {

    @GET("api/v1/status")
    suspend fun getMinerStatus(): MinerStatus

    companion object {

        var BASE_URL = ""
        var isInit = false
        lateinit var instance: IKuaiApiService

        fun create(): IKuaiApiService {
            if(isInit) return instance
            val okHttpClient = OkHttpManager.instance.okHttpClient
            val gson = GsonManager.instance.gson
            instance = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
                .create(IKuaiApiService::class.java)
            isInit = true
            return instance
        }
    }
}