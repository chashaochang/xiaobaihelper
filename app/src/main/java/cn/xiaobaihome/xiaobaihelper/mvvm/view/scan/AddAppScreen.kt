package cn.xiaobaihome.xiaobaihelper.mvvm.view.scan

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAppScreen(navController: NavController){
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            SmallTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, "")
                    }
                },
                title = {
                    Text("添加应用")
                }
            )
        }
    ) {

    }
}

