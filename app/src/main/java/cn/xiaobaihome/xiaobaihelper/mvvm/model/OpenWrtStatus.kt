package cn.xiaobaihome.xiaobaihelper.mvvm.model

data class OpenWrtStatus(
    val conncount: Int = 0,
    val connmax: Int = 0,
    val cpuinfo: String = "",
    val cpuusage: String = "",
    val ethinfo: String = "",
    val leases: List<Any> = listOf(),
    val leases6: List<Any> = listOf(),
    val loadavg: List<Int> = listOf(),
    val localtime: String = "",
    val memcached: String = "",
    val memory: Memory = Memory(),
    val swap: Swap = Swap(),
    val uptime: Int = 0,
    val userinfo: String = "",
    val wan: Wan = Wan(),
    val wifinets: List<Any> = listOf()
) {
    data class Memory(
        val available: Int = 0,
        val buffered: Int = 0,
        val cached: Int = 0,
        val free: Int = 0,
        val shared: Int = 0,
        val total: Int = 0
    )

    data class Swap(
        val free: Int = 0,
        val total: Int = 0
    )

    data class Wan(
        val dns: List<String> = listOf(),
        val expires: Int = 0,
        val gwaddr: String = "",
        val ifname: String = "",
        val ipaddr: String = "",
        val link: String = "",
        val netmask: String = "",
        val proto: String = "",
        val uptime: Int = 0
    )
}