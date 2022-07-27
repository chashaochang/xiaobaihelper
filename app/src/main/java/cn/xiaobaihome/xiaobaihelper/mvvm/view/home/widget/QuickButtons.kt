package cn.xiaobaihome.xiaobaihelper.mvvm.view.home.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.xiaobaihome.xiaobaihelper.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickButtons() {
    val list = listOf(
        Shortcut(R.mipmap.logo_ikuai,"爱快"),
        Shortcut(R.mipmap.logo_openwrt,"OpenWrt"),
        Shortcut(R.mipmap.logo_unraid,"UnRaid"),
        Shortcut(R.mipmap.icon_all,"全部")
    )
    Row {
        for (item in list) {
                Card(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(modifier = Modifier.size(40.dp), painter = painterResource(id = item.icon),contentDescription = "")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = item.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
                if(item != list.last()) {
                    Spacer(modifier = Modifier.width(8.dp))
                }
        }
    }
}

class Shortcut(
    val icon: Int,
    val title: String,
    val onClick: () -> Unit= {}
)