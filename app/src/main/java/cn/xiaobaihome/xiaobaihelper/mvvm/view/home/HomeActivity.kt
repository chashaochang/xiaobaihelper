@file:OptIn(ExperimentalMaterial3Api::class)

package cn.xiaobaihome.xiaobaihelper.mvvm.view.home

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseActivity
import cn.xiaobaihome.xiaobaihelper.mvvm.model.ApkInfo
import cn.xiaobaihome.xiaobaihelper.mvvm.model.RespResult
import cn.xiaobaihome.xiaobaihelper.api.Utils
import cn.xiaobaihome.xiaobaihelper.helper.getVersion
import cn.xiaobaihome.xiaobaihelper.mvvm.view.RouterConfig
import cn.xiaobaihome.xiaobaihelper.mvvm.view.registerRouter
import cn.xiaobaihome.xiaobaihelper.mvvm.view.scan.AddAppScreen
import cn.xiaobaihome.xiaobaihelper.theme.XBHelperTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private var exitTime: Long = 0
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, RouterConfig.Home) {
                registerRouter(navController)
            }
        }
        //检查更新
        launch {
            homeViewModel.getVersion(object :
                RespResult<ApkInfo> {
                override fun onSuccess(t: ApkInfo) {

                    if (t.versionCode > getVersion()) {
                        alert(t.updateInfo.toString(), "更新") { _, _ ->
                            Utils.downLoadNew(this@HomeActivity, t.downloadUrl)
                        }
                    }
                }

                override fun onError(msg: String) {
                    alert(msg)
                }
            })
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if (System.currentTimeMillis() - exitTime > 2000) {
                toast("再按一次退出程序")
                exitTime = System.currentTimeMillis()
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                exitProcess(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}

@Composable
fun HomeContent(navController: NavController) {
    val homeNavController = rememberNavController()
    // tab标题
    val items = listOf(
        Screen.Home,
        Screen.Like,
        Screen.Mine
    )
    XBHelperTheme {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier.navigationBarsPadding(),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    NavigationBar {
                        val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        items.forEach { screen ->
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        painter = painterResource(id = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) screen.iconSelected else screen.iconUnSelected),
                                        contentDescription = ""
                                    )
                                },
                                label = {
                                    Text(text = stringResource(id = screen.resourceId))
                                },
                                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                onClick = {
                                    homeNavController.navigate(screen.route) {
                                        popUpTo(homeNavController.graph.findStartDestination().id) {
                                            saveState = false
                                        }
                                        launchSingleTop = true
                                        restoreState = false
                                    }
                                })
                        }
                    }
                }
            }
        ) {
            NavHost(
                navController = homeNavController,
                startDestination = Screen.Home.route,
                Modifier.padding(it)
            ) {
                composable(Screen.Home.route) { HomeScreen(navController) }
                composable(Screen.Like.route) { LikeScreen(navController) }
                composable(Screen.Mine.route) { MineScreen(navController) }
            }
        }
    }
}

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val iconSelected: Int,
    @DrawableRes val iconUnSelected: Int
) {
    object Home :
        Screen("home", R.string.home, R.mipmap.btn_icon_home_activated, R.mipmap.btn_icon_home)

    object Like :
        Screen("like", R.string.like, R.mipmap.btn_icon_follow_activated, R.mipmap.btn_icon_follow)

    object Mine :
        Screen("mine", R.string.mine, R.mipmap.btn_icon_mine_activated, R.mipmap.btn_icon_mine)
}