@file:OptIn(ExperimentalMaterial3Api::class)

package cn.xiaobaihome.xiaobaihelper.mvvm.view.acount

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.helper.AppData
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseActivity
import cn.xiaobaihome.xiaobaihelper.theme.XBHelperTheme
import cn.xiaobaihome.xiaobaihelper.theme.pagePadding

class AddAccountActivity : BaseActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                                Text("添加账户")
                            }
                        )
                        AddAccountContent()
                    }
                }
            }
        }
    }
}

@Composable
fun AddAccountContent() {
    val context = LocalContext.current
    val list = listOf(
        AccountItem(
            icon = R.mipmap.icon_wifi_router,
            title = "iKuai",
            subTitle = "软路由",
            0,
            !AppData.isIKuaiLogin.collectAsState().value
        ),
        AccountItem(
            icon = R.mipmap.icon_wifi_router,
            title = "openWrt",
            subTitle = "软路由",
            1,
            !AppData.isOpenWrtLogin.collectAsState().value
        ),
        AccountItem(
            icon = R.mipmap.icon_nas,
            title = "unRaid",
            subTitle = "nas",
            2,
        ),
        AccountItem(
            icon = R.mipmap.icon_miner,
            title = "NBMiner",
            subTitle = "miner",
            3,
            enable = !AppData.isMinerLogin.collectAsState().value
        ),
        AccountItem(
            icon = R.mipmap.icon_ask,
            title = "待开发",
            subTitle = "待开发",
            -1,
            enable = false
        )
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(pagePadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(list) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                enabled = it.enable,
                onClick = {
                    context.startActivity(
                        Intent(
                            context,
                            LoginAccountActivity::class.java
                        ).apply { putExtra("type", it.type) })
                },
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(16.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = it.icon),
                        contentDescription = it.title,
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Column {
                        Text(text = it.title, fontSize = 16.sp)
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(text = it.subTitle, fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

class AccountItem(
    @DrawableRes val icon: Int,
    val title: String,
    val subTitle: String,
    val type: Int,
    val enable: Boolean = true
)

@Preview
@Composable
fun AddAccountContentPreview() {
    AddAccountContent()
}