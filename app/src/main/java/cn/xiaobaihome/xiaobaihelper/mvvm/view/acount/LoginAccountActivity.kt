@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package cn.xiaobaihome.xiaobaihelper.mvvm.view.acount

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cn.xiaobaihome.xiaobaihelper.helper.AppData
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseActivity
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseViewModel
import cn.xiaobaihome.xiaobaihelper.theme.XBHelperTheme
import cn.xiaobaihome.xiaobaihelper.theme.pagePadding
import cn.xiaobaihome.xiaobaihelper.widget.alert
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginAccountActivity : BaseActivity() {

    private val viewModel: LoginAccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.type.value = intent.getIntExtra("type", -1)
        setContent {
            XBHelperTheme {
                Scaffold {
                    Column(
                        modifier = Modifier.statusBarsPadding(),
                    ) {
                        SmallTopAppBar(
                            navigationIcon = {
                                IconButton(onClick = {
                                    finish()
                                }) {
                                    Icon(Icons.Default.ArrowBack, "")
                                }
                            },
                            title = {
                                Text("账户登录")
                            },
                        )
                        LoginContent(viewModel) { protocol, address, port, username, pwd ->
                            launch {
                                viewModel.testLogin(
                                    protocol,
                                    address,
                                    port,
                                    username,
                                    pwd
                                ) { success, msg ->
                                    if (success) {
                                        toast("登录成功")
                                        finish()
                                    } else {
                                        toast(msg?:"登录失败")
                                    }
                                }
                            }
                        }
                    }
                }
            }
            alert(show = viewModel.type.value < 0, text = "账户类型传递错误") {
                finish()
            }
        }
    }
}

@Composable
fun LoginContent(
    viewModel: LoginAccountViewModel, onLoginClick: (
        protocol: String,
        address: String,
        port: String,
        username: String,
        pwd: String
    ) -> Unit
) {
    var protocol by remember { mutableStateOf("http") }
    var address by remember { mutableStateOf("192.168.1.1") }
    var port by remember { mutableStateOf("80") }
    var username by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }
    Column(
        Modifier.padding(pagePadding, 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(onClick = {}, enabled = false) {
            Text(
                text = when (viewModel.type.value) {
                    0 -> "iKuai"
                    1 -> "openWrt"
                    2 -> "unRaid"
                    3 -> "NBMiner"
                    else -> "未知"
                }, color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(top = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier.width(66.dp),
                    value = protocol,
                    label = { Text(text = "协议") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    onValueChange = {
                        protocol = it
                    }
                )
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = address,
                    label = { Text(text = "网址") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    onValueChange = {
                        address = it
                    }
                )
                OutlinedTextField(
                    modifier = Modifier.width(90.dp),
                    value = port,
                    label = { Text(text = "端口") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    onValueChange = {
                        port = it
                    }
                )
            }
        }
        if (viewModel.type.value != 3) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        modifier = Modifier.padding(top = 14.dp),
                        value = username,
                        label = { Text(text = "账号") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        onValueChange = {
                            username = it
                        }
                    )
                }
//                Spacer(modifier = Modifier.width(10.dp))
//                Card(modifier = Modifier.weight(1f)) {
//                    OutlinedTextField(
//                        modifier = Modifier.padding(top = 14.dp),
//                        value = "",
//                        label = { Text(text = "备注") },
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            focusedBorderColor = Color.Transparent,
//                            unfocusedBorderColor = Color.Transparent
//                        ),
//                        onValueChange = {}
//                    )
//                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Card(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp),
                    value = pwd,
                    label = { Text(text = "密码") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    onValueChange = {
                        pwd = it
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            onLoginClick(
                protocol,
                address,
                port,
                username,
                pwd
            )
        }) {
            Text(text = "登录并存储")
        }
    }
}