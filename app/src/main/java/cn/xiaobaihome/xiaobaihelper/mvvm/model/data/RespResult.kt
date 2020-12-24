package cn.xiaobaihome.xiaobaihelper.mvvm.model.data

interface RespResult<T> {
    fun onSuccess(t: T)
    fun onError(msg: String)
}