package cn.xiaobaihome.xiaobaihelper.mvvm.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import cn.xiaobaihome.xiaobaihelper.mvvm.view.home.HomeContent
import cn.xiaobaihome.xiaobaihelper.mvvm.view.scan.AddAppScreen

object RouterConfig {

    const val Home = "home"
    const val AddAppScreen = "add_app"
}

fun NavGraphBuilder.registerRouter(navController: NavController) {
    composable(RouterConfig.Home) { HomeContent(navController) }
    composable(RouterConfig.AddAppScreen) { AddAppScreen(navController) }
}