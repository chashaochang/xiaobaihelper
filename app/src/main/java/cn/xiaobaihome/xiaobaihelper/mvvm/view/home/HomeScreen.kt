package cn.xiaobaihome.xiaobaihelper.mvvm.view.home

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.xiaobaihome.xiaobaihelper.helper.AppData
import cn.xiaobaihome.xiaobaihelper.mvvm.view.acount.AddAccountActivity
import cn.xiaobaihome.xiaobaihelper.mvvm.view.home.widget.MinerCard
import com.google.zxing.client.android.CaptureActivity
import kotlinx.coroutines.runBlocking
import java.util.*

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    val expandState = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
//        homeViewModel.loadData()
    }
    Column(
        modifier = Modifier.statusBarsPadding()
    ) {
        SmallTopAppBar(
            title = { Text("控制台") },
            actions = {
                IconButton(onClick = {
                    expandState.value = true
                }) {
                    Icon(Icons.Filled.Add, "")
                }
                DropdownMenu(
                    expanded = expandState.value,
                    onDismissRequest = { expandState.value = false }
                ) {
                    DropdownMenuItem(text = { Text(text = "添加账号", fontSize = 16.sp) }, onClick = {
                        expandState.value = false
                        context.startActivity(Intent(context, AddAccountActivity::class.java))
                    })
                    DropdownMenuItem(text = { Text(text = "扫一扫", fontSize = 16.sp) }, onClick = {
                        expandState.value = false
                        context.startActivity(Intent(context, CaptureActivity::class.java))
                    })
                }
            }
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp, 10.dp)
                .fillMaxWidth()
        ) {
            val isMinerLogin = AppData.isMinerLogin.collectAsState().value
            if (isMinerLogin) {
                val timer = remember {
                    Timer().apply {
                        val task = object : TimerTask() {
                            override fun run() {
                                runBlocking {
                                    homeViewModel.getMinerStatus()
                                }
                            }
                        }
                        scheduleAtFixedRate(task, 3000L, 3000L)
                    }
                }
                val minerStatus = homeViewModel.minerStatus.collectAsState().value
                if (minerStatus.version.isNotBlank()) {
                    MinerCard(minerStatus)
                } else {
                    Text("暂无数据")
                }
            } else {
                Text(text = "什么也没有，快去添加设备吧")
            }
        }
    }
}