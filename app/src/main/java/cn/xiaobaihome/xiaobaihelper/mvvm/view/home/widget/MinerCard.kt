@file:OptIn(ExperimentalMaterial3Api::class)

package cn.xiaobaihome.xiaobaihelper.mvvm.view.home.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cn.xiaobaihome.xiaobaihelper.mvvm.model.MinerStatus

@Composable
fun MinerCard(minerStatus: MinerStatus) {

    Card {
        Column {
            Text(text = "显卡信息")
            Text("显卡:${minerStatus.miner.devices[0].info}")
            Text("算力:${minerStatus.miner.devices[0].hashrate}")
            Text("接受份额:${minerStatus.miner.devices[0].accepted_shares}")
            Text("拒绝份额:${minerStatus.miner.devices[0].rejected_shares}")
            Text("无效份额:${minerStatus.miner.devices[0].invalid_shares}")
            Text("核心温度:${minerStatus.miner.devices[0].temperature}")
            Text("显存温度:${minerStatus.miner.devices[0].memTemperature}")
            Text("功耗:${minerStatus.miner.devices[0].power}")
            Text("风扇:${minerStatus.miner.devices[0].fan}")
            Divider()
            Text(text = "矿池信息")
            Text("当前难度:${minerStatus.stratum.difficulty}")
            Text("矿池延迟:${minerStatus.stratum.latency}")
            Text("10分钟算力:${minerStatus.stratum.pool_hashrate_10m}")
            Text("4小时算力:${minerStatus.stratum.pool_hashrate_4h}")
            Text("24小时算力:${minerStatus.stratum.pool_hashrate_24h}")
        }
    }
}