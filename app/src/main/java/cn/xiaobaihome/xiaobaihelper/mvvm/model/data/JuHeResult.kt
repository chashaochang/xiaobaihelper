package cn.xiaobaihome.xiaobaihelper.mvvm.model.data

import com.google.gson.annotations.SerializedName

class JuHeResult {
    var stat: String? = null
    @SerializedName("data")
    var data1: List<NewItem>? = null
}