package cn.xiaobaihome.xiaobaihelper.api

import cn.xiaobaihome.xiaobaihelper.bean.IkuaiBaseReq
import cn.xiaobaihome.xiaobaihelper.bean.LoginBean
import cn.xiaobaihome.xiaobaihelper.mvvm.model.IKuaiDefaultResult
import cn.xiaobaihome.xiaobaihelper.mvvm.model.IKuaiStatus
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface IKuaiApiService {

    @Headers("type:ikuai")
    @POST("/Action/login")
    suspend fun login(@Body loginBean:LoginBean): IKuaiDefaultResult<Any>

    @Headers("type:ikuai")
    @POST("/Action/call")
    suspend fun getStatus(@Body req:IkuaiBaseReq): IKuaiDefaultResult<IKuaiStatus>

    companion object {

        var BASE_URL = ""
        var isInit = false
        lateinit var instance: IKuaiApiService

        fun create(): IKuaiApiService {
            if (isInit) return instance
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