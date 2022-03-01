package cn.xiaobaihome.xiaobaihelper.api

import cn.xiaobaihome.xiaobaihelper.mvvm.model.DefaultResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 网络请求回调封装
 */
abstract class HttpCallBack<T> : Callback<DefaultResult<T?>?> {

    override fun onResponse(
        call: Call<DefaultResult<T?>?>,
        response: Response<DefaultResult<T?>?>
    ) {
        if (response.code() == 200) {
            val httpResult = response.body()
            if (httpResult != null) {
                if (2000 == httpResult.code) {
                    onSuccess(
                        httpResult.data,
                        httpResult.code,
                        httpResult.msg,
                        httpResult
                    )
                } else {
                    onFailure(httpResult.msg, httpResult.code)
                }
            } else {
                onFailure("数据异常", 4004)
            }
        } else {
            onFailure("请求失败,错误信息:<br/>" + response.message(), response.code())
        }
    }

    override fun onFailure(call: Call<DefaultResult<T?>?>, t: Throwable) {
        //判断提示  网络错误、404之类的
        //按需调用 onFailure()
        val message = "请求失败,请稍后再试"
        onFailure(message, 666, t)
    }

    abstract fun onSuccess(
        data: T?,
        code: Int,
        message: String?,
        httpResult: DefaultResult<T?>?
    )

    private fun onFailure(message: String, code: Int, t: Throwable? = null) {
        throw ApiException(message, code, t)
    }

}