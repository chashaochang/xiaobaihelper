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
import cn.xiaobaihome.xiaobaihelper.mvvm.model.MinerStatus

@Composable
fun MinerCard(minerStatus: MinerStatus) {
    Card() {
        Column(
            modifier = Modifier.padding(
                top = 12.dp,
                end = 16.dp,
                bottom = 16.dp,
                start = 0.dp
            ).fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "NBMiner",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row() {
                Icon(
                    modifier = Modifier.size(80.dp),
                    painter = painterResource(id = R.mipmap.graphics_card),
                    contentDescription = ""
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(minerStatus.miner.devices[0].info, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        Text(text = "算力:", fontSize = 14.sp)
                        Text(minerStatus.miner.devices[0].hashrate, fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row() {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "风扇:${minerStatus.miner.devices[0].fan}%",
                            fontSize = 14.sp
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "显存:${minerStatus.miner.devices[0].memTemperature}℃",
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row() {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "功耗:${minerStatus.miner.devices[0].power}W",
                            fontSize = 14.sp
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "核心:${minerStatus.miner.devices[0].temperature}℃",
                            fontSize = 14.sp
                        )

                    }
                }
            }
//            Text("接受份额:${minerStatus.miner.devices[0].accepted_shares}")
//            Text("拒绝份额:${minerStatus.miner.devices[0].rejected_shares}")
//            Text("无效份额:${minerStatus.miner.devices[0].invalid_shares}")
//
//            Divider()
//            Text(text = "矿池信息")
//            Text("当前难度:${minerStatus.stratum.difficulty}")
//            Text("矿池延迟:${minerStatus.stratum.latency}")
//            Text("10分钟算力:${minerStatus.stratum.pool_hashrate_10m}")
//            Text("4小时算力:${minerStatus.stratum.pool_hashrate_4h}")
//            Text("24小时算力:${minerStatus.stratum.pool_hashrate_24h}")
        }
    }
}