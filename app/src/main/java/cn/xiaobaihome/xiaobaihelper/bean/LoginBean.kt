package cn.xiaobaihome.xiaobaihelper.bean

data class LoginBean(
    val username: String = "",
    val pass: String = "",
    val passwd: String = "",
    val remember_password: Boolean = false,
)