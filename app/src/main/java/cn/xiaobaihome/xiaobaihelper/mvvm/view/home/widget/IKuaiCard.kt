@file:OptIn(ExperimentalMaterial3Api::class)

package cn.xiaobaihome.xiaobaihelper.mvvm.view.home.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.helper.formatSize
import cn.xiaobaihome.xiaobaihelper.helper.parseTime
import cn.xiaobaihome.xiaobaihelper.mvvm.model.IKuaiStatus

@Composable
fun IKuaiCard(iKuaiStatus: IKuaiStatus) {
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
                text = "iKuai",
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
                    Text("免费版 " + iKuaiStatus.sysstat.verinfo.verstring, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row() {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "温度:" + iKuaiStatus.sysstat.cputemp[0].toString().plus("℃"),
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("CPU:" + iKuaiStatus.sysstat.cpu[0], fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("内存:" + iKuaiStatus.sysstat.memory.used, fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "运行:" + parseTime(iKuaiStatus.sysstat.uptime),
                                fontSize = 14.sp
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "外网:" + if (iKuaiStatus.sysstat.link_status > 0) "已断开" else "已连接",
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "上行:" + formatSize(iKuaiStatus.sysstat.stream.upload.toLong()),
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "下行:" + formatSize(iKuaiStatus.sysstat.stream.download.toLong()),
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "",
                                fontSize = 14.sp
                            )
                        }
                    }

                }
            }
        }
    }
}