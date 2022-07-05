package cn.xiaobaihome.xiaobaihelper.mvvm.view.home

import android.content.Intent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cn.xiaobaihome.xiaobaihelper.mvvm.view.RouterConfig
import com.google.zxing.client.android.CaptureActivity

@Composable
fun HomeScreen(navController: NavController) {
    val expandState = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
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
                    DropdownMenuItem(text = { Text(text = "添加应用", fontSize = 16.sp) }, onClick = {
                        navController.navigate(RouterConfig.AddAppScreen)
                    })
                    DropdownMenuItem(text = { Text(text = "扫一扫", fontSize = 16.sp) }, onClick = {
                        context.startActivity(Intent(context, CaptureActivity::class.java))
                    })
                }
            }
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text(text = "什么也没有，快去添加设备吧")

        }
    }
}