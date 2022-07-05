package cn.xiaobaihome.xiaobaihelper.api

import cn.xiaobaihome.xiaobaihelper.helper.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager private constructor(){

    companion object{
        val instance : RetrofitManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitManager().apply {
                init()
            }
        }

    }

    lateinit var retrofit: Retrofit

    private fun init() {
        val okHttpClient = OkHttpManager.instance.okHttpClient
        val gson = GsonManager.instance.gson
        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Constant.HOST_API)
            .client(okHttpClient)
            .build()
    }
}