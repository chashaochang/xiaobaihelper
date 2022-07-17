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
import cn.xiaobaihome.xiaobaihelper.mvvm.view.home.widget.IKuaiCard
import cn.xiaobaihome.xiaobaihelper.mvvm.view.home.widget.MinerCard
import cn.xiaobaihome.xiaobaihelper.mvvm.view.home.widget.OpenWrtCard
import com.google.zxing.client.android.CaptureActivity
import kotlinx.coroutines.runBlocking
import java.util.*

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    val expandState = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val isIKuaiLogin = AppData.isIKuaiLogin.collectAsState().value
    if (isIKuaiLogin) {
        remember {
            Timer().apply {
                val task = object : TimerTask() {
                    override fun run() {
                        runBlocking {
                            homeViewModel.getIKuaiStatus()
                        }
                    }
                }
                scheduleAtFixedRate(task, 10L, 5000L)
            }
        }
    }
    val ikuaiStatus = homeViewModel.ikuaiStatus.collectAsState().value

    val isOpenWrtLogin = AppData.isOpenWrtLogin.collectAsState().value
    if (isOpenWrtLogin) {
        LaunchedEffect(Unit){
            homeViewModel.getOpenWrtInfoHtml()
        }
        remember {
            Timer().apply {
                val task = object : TimerTask() {
                    override fun run() {
                        runBlocking {
                            homeViewModel.getOpenWrtStatus()
                        }
                    }
                }
                scheduleAtFixedRate(task, 10L, 5000L)
            }
        }
    }
    val openWrtStatus = homeViewModel.openWrtStatus.collectAsState().value
    val openWrtInfo = homeViewModel.openWrtInfo.collectAsState().value

    val isMinerLogin = AppData.isMinerLogin.collectAsState().value
    if (isMinerLogin) {
        remember {
            Timer().apply {
                val task = object : TimerTask() {
                    override fun run() {
                        runBlocking {
                            homeViewModel.getMinerStatus()
                        }
                    }
                }
                scheduleAtFixedRate(task, 0L, 3000L)
            }
        }
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
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp, 10.dp)
                .fillMaxWidth()
        ) {
            if (isIKuaiLogin && ikuaiStatus.sysstat.hostname.isNotEmpty()) {
                IKuaiCard(ikuaiStatus)
            } else {
                Text("暂无ikuai数据")
            }
            if (isOpenWrtLogin && openWrtInfo.name.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                OpenWrtCard(openWrtInfo,openWrtStatus)
            } else {
                Text("暂无openwrt数据")
            }
            if (isMinerLogin) {
                val minerStatus = homeViewModel.minerStatus.collectAsState().value
                if (minerStatus.version.isNotBlank()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    MinerCard(minerStatus)
                } else {
                    Text("暂无miner数据")
                }
            } else {
                Text(text = "什么也没有，快去添加设备吧")
            }
        }
    }
}