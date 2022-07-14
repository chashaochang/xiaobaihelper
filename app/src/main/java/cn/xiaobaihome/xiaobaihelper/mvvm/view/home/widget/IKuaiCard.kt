@file:OptIn(ExperimentalMaterial3Api::class)

package cn.xiaobaihome.xiaobaihelper.mvvm.view.home.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.xiaobaihome.xiaobaihelper.mvvm.model.IKuaiStatus

@Composable
fun IKuaiCard(iKuaiStatus: IKuaiStatus) {
    Card() {
        Column(modifier = Modifier.padding(top = 16.dp, end = 16.dp, bottom = 16.dp, start = 0.dp)) {
            Text("系统状态", fontSize = 20.sp)
            Text(iKuaiStatus.sysstat.hostname, fontSize = 14.sp)
            Text(iKuaiStatus.sysstat.verinfo.verstring, fontSize = 14.sp)
            Text(iKuaiStatus.sysstat.cputemp[0].toString().plus("℃"), fontSize = 14.sp)
            Text(iKuaiStatus.sysstat.cpu[0], fontSize = 14.sp)
            Text(iKuaiStatus.sysstat.memory.used, fontSize = 14.sp)

        //            Row() {
//                Icon(
//                    modifier = Modifier.size(80.dp),
//                    painter = painterResource(id = R.mipmap.graphics_card),
//                    contentDescription = ""
//                )
//                Column(modifier = Modifier.weight(1f)) {
//                    Text(iKuaiStatus.miner.devices[0].info, fontSize = 14.sp)
//                    Spacer(modifier = Modifier.height(4.dp))
//                    Row {
//                        Text(text = "算力:", fontSize = 12.sp)
//                        Text(iKuaiStatus.miner.devices[0].hashrate, fontSize = 12.sp)
//                    }
//                    Spacer(modifier = Modifier.height(4.dp))
//                    Row() {
//                        Text(
//                            modifier = Modifier.weight(1f),
//                            text = "核心温度:${minerStatus.miner.devices[0].temperature}℃",
//                            fontSize = 12.sp
//                        )
//                        Text(
//                            modifier = Modifier.weight(1f),
//                            text = "显存温度:${minerStatus.miner.devices[0].memTemperature}℃",
//                            fontSize = 12.sp
//                        )
//                    }
//                    Spacer(modifier = Modifier.height(4.dp))
//                    Row() {
//                        Text(
//                            modifier = Modifier.weight(1f),
//                            text = "功耗:${minerStatus.miner.devices[0].power}W",
//                            fontSize = 12.sp
//                        )
//                        Text(
//                            modifier = Modifier.weight(1f),
//                            text = "风扇:${minerStatus.miner.devices[0].fan}%",
//                            fontSize = 12.sp
//                        )
//                    }
//                }
//            }
        }
    }
}