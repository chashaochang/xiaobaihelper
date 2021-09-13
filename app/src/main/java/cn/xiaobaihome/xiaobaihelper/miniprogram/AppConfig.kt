package cn.xiaobaihome.xiaobaihelper.miniprogram

import androidx.ui.graphics.Color

data class AppConfig(
    val pages: List<String>,
    val window: Window,
    val tabBar: TabBar
)

data class Window(
    val backgroundTextStyle: String,
    val navigationBarBackgroundColor: String,
    val navigationBarTextStyle: String,
    val navigationBarTitleText: String
)

data class TabBar(
    val color: Int,
    val selectedColor: Int,
    val backgroundColor: Int,
    val borderStyle: String,
    val list: List<TabBarItem>,
    val position: String,
    val custom: Boolean
)

data class TabBarItem(
    val pagePath: String,
    val text: String,
    val iconPath: String,
    val selectedIconPath: String
)