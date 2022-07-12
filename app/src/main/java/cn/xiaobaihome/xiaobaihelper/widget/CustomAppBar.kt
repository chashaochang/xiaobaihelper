package cn.xiaobaihome.xiaobaihelper.widget

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.xiaobaihome.xiaobaihelper.R

/**
 * @param onMenuClick 返回被点击Icon下标,不用resource因为是可变的
 * */
@Composable
fun CustomAppBar(
    isIconDark: Boolean = true,
    menuList: MutableList<MenuItem>,
    backgroundAlpha: Float = 1f,
    onMenuClick: (Int) -> Unit,
    content: @Composable () -> Unit
) {
//    val context = LocalContext.current
//    Box(
//        Modifier
//            .height(56.dp)
//            .statusBarsPadding()
//            .fillMaxWidth()
//    ) {
//        Surface(
//            color = Color.White, modifier = Modifier
//                .fillMaxWidth()
//                .alpha(backgroundAlpha)
//        ) {
//            Column(modifier = Modifier.height(56.dp).statusBarsPadding()) {
//
//            }
//        }
//        Column {
//            Spacer(modifier = Modifier.statusBarsPadding())
//            TopAppBar(
//                backgroundColor = Color.Transparent,
//                elevation = 0.dp,
//                modifier = Modifier.statusBarsPadding()
//            ) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                ) {
//                    Spacer(modifier = Modifier.width(16.dp))
//                    Image(
//                        painter = painterResource(id = if (isIconDark) R.mipmap.ic_arrow_left else R.mipmap.ic_arrow_left1),
//                        contentDescription = "返回",
//                        modifier = Modifier.clickable(onClick = {
//                            if (context is Activity) {
//                                context.finish()
//                            }
//                        })
//                    )
//                    Box(modifier = Modifier.weight(1f)) {
//                        content()
//                    }
//                    menuList.forEachIndexed { index, i ->
//                        val count = if (i.count > 99) {
//                            "99+"
//                        } else {
//                            i.count.toString()
//                        }
//                        Box(
//                            modifier = Modifier
//                                .fillMaxHeight()
//                                .padding(0.dp, 8.dp),
//                            contentAlignment = Alignment.TopEnd
//                        ) {
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxHeight()
//                                    .align(Alignment.Center)
//                            ) {
//                                Image(
//                                    painter = painterResource(id = i.icon),
//                                    contentDescription = "",
//                                    modifier = Modifier
//                                        .clickable(onClick = {
//                                            onMenuClick(index)
//                                        })
//                                        .fillMaxHeight()
//                                )
//                                Spacer(modifier = Modifier.width(16.dp))
//                            }
//                            if (i.count > 0) {
//                                Row {
//                                    Surface(
//                                        color = Color.danger,
//                                        shape = RoundedCornerShape(16.dp),
//                                        border = BorderStroke(1.dp, Color.White),
//                                    ) {
//                                        Text(
//                                            text = count,
//                                            color = Color.White,
//                                            fontSize = 10.sp,
//                                            modifier = Modifier
//                                                .padding(2.dp, 0.dp)
//                                                .defaultMinSize(16.dp, 16.dp),
//                                            textAlign = TextAlign.Center
//                                        )
//                                    }
//                                    Spacer(modifier = Modifier.width(8.dp))
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
}

@Preview
@Composable
fun AppBarPreview() {
//    val menuList = mutableListOf(
//        MenuItem(R.mipmap.toolbar_msg_dark, 100),
//        MenuItem(R.mipmap.toolbar_share_dark)
//    )
//    CustomAppBar(isIconDark = true, menuList = menuList, backgroundAlpha = 1f, onMenuClick = {}){}
}

class MenuItem(val icon: Int, val count: Int) {
    constructor(icon: Int) : this(icon, 0)
}