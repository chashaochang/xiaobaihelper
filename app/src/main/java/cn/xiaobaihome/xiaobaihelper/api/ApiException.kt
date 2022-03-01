package cn.xiaobaihome.xiaobaihelper.api

/**
 * Created by dzq on 2016/10/18.
 */

class ApiException(override var message: String,var code: Int,var t: Throwable? = null) : Exception()
