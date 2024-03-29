package cn.xiaobaihome.xiaobaihelper.helper

import kotlinx.coroutines.flow.MutableStateFlow

object AppData {

    var isMinerLogin = MutableStateFlow(false)
    var isIKuaiLogin = MutableStateFlow(false)
    var isOpenWrtLogin = MutableStateFlow(false)

    val JXURL1 = "https://www.administratorw.com/video.php?url="
    val JXURL2 = "http://jx.aeidu.cn/index.php?url="
    val JXURL3 = "http://jx.598110.com/?url="
    val JXURL4 = "http://jiexi.071811.cc/jx2.php?url="
    val JXURL5 = "http://api.91exp.com/svip/?url="
}