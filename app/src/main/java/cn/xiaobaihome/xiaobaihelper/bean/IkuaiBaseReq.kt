package cn.xiaobaihome.xiaobaihelper.bean

data class IkuaiBaseReq(
    val action: String = "",
    val func_name: String = "",
    val `param`: Param = Param()
) {
    data class Param(
        val TYPE: String = ""
    )
}