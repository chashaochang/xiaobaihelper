package cn.xiaobaihome.xiaobaihelper.mvvm.model

interface RespResult<T> {
    fun onSuccess(t: T)
    fun onError(msg: String)
}