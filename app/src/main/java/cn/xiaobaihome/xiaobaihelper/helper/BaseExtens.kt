package cn.xiaobaihome.xiaobaihelper.helper

import android.content.Context

/**
 * 页面描述：一些扩展
 */

fun Context.getVersion(): Int {
    val info = packageManager?.getPackageInfo(packageName.toString(), 0)
    if (info?.longVersionCode == null) {
        return 0
    }
    return info.longVersionCode.toInt()
}