package cn.xiaobaihome.xiaobaihelper.miniprogram

data class AppConfig(
    val pages: List<String>,
    val window: Window
)

data class Window(
    val backgroundTextStyle: String,
    val navigationBarBackgroundColor: String,
    val navigationBarTextStyle: String,
    val navigationBarTitleText: String
)