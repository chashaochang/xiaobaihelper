@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)

package cn.xiaobaihome.xiaobaihelper.mvvm.view.home

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.api.MinerApiService
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseActivity
import cn.xiaobaihome.xiaobaihelper.api.Utils
import cn.xiaobaihome.xiaobaihelper.helper.AppData
import cn.xiaobaihome.xiaobaihelper.helper.CacheUtil
import cn.xiaobaihome.xiaobaihelper.helper.getVersion
import cn.xiaobaihome.xiaobaihelper.theme.XBHelperTheme
import cn.xiaobaihome.xiaobaihelper.widget.alert
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@ExperimentalAnimationApi
@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private var exitTime: Long = 0
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeContent(homeViewModel)
            alert(
                show = homeViewModel.alertState.collectAsState().value.isNotEmpty(),
                text = homeViewModel.alertState.collectAsState().value,
                onConfirm = {
                    homeViewModel.alertState.value = ""
                }
            )

            LaunchedEffect(Unit) {
//                homeViewModel.loadData()
//                homeViewModel.getVersion()
            }

            alert(
                homeViewModel.apkInfo.value.versionCode > getVersion(),
                text = homeViewModel.apkInfo.value.updateInfo.toString(),
                confirmText = "更新",
                onConfirm = {
                    homeViewModel.apkInfo.value.downloadUrl?.let {
                        Utils.downLoadNew(
                            this@HomeActivity,
                            it
                        )
                    }
                })
        }
        //初始化Miner
        val minerProtocol = CacheUtil.get(CacheUtil.MINER_PROTOCOL)
        val minerAddress = CacheUtil.get(CacheUtil.MINER_ADDRESS)
        val minerPort = CacheUtil.get(CacheUtil.MINER_PORT)
        if (!minerProtocol.isNullOrBlank() && !minerAddress.isNullOrBlank() && !minerPort.isNullOrBlank()) {
            AppData.isMinerLogin.value = true
            MinerApiService.BASE_URL = "${minerProtocol}://${minerAddress}:${minerPort}"
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
fun HomeContent(homeViewModel: HomeViewModel) {
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
                composable(Screen.Home.route) { HomeScreen(homeViewModel) }
                composable(Screen.Like.route) { LikeScreen() }
                composable(Screen.Mine.route) { MineScreen() }
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