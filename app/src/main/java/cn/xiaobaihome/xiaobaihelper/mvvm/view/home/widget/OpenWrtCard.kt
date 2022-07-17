@file:OptIn(ExperimentalMaterial3Api::class)

package cn.xiaobaihome.xiaobaihelper.mvvm.view.home.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.helper.parseTimeOp
import cn.xiaobaihome.xiaobaihelper.mvvm.model.OpenWrtInfo
import cn.xiaobaihome.xiaobaihelper.mvvm.model.OpenWrtStatus

@Composable
fun OpenWrtCard(openWrtInfo: OpenWrtInfo, openWrtStatus: OpenWrtStatus) {
    Card() {
        Column(
            modifier = Modifier
                .padding(
                    top = 12.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                    start = 0.dp
                )
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "OpenWrt",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row() {
                Icon(
                    modifier = Modifier
                        .size(80.dp)
                        .padding(start = 16.dp, end = 16.dp, bottom = 0.dp, top = 0.dp),
                    painter = painterResource(id = R.mipmap.pic_soft_router),
                    contentDescription = ""
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "主机型号:" + openWrtInfo.device,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("CPU信息:" + openWrtStatus.cpuinfo.trim().replace("\n",""), fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "固件版本:" + openWrtInfo.firmwareVersion, fontSize = 14.sp, maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("内核版本:" + openWrtInfo.kernelVersion, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("本地时间:" + openWrtStatus.localtime, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("运行时间:" + parseTimeOp(openWrtStatus.uptime), fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("CPU使用率:" + openWrtStatus.cpuusage.replace("\n",""), fontSize = 14.sp)
                }
            }
        }
    }
}