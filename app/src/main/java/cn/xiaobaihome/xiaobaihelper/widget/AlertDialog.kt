package cn.xiaobaihome.xiaobaihelper.widget

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun alert(
    show: Boolean,
    title: String = "提示",
    text: String,
    confirmText: String = "确定",
    cancelText: String = "取消",
    showCancel: Boolean = false,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {}
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onConfirm)
                { Text(text = confirmText) }
            },
            dismissButton = {
                if (showCancel) {
                    TextButton(onClick = onDismiss)
                    { Text(text = cancelText) }
                }
            },
            title = { Text(text = title) },
            text = { Text(text = text) }
        )
    }
}