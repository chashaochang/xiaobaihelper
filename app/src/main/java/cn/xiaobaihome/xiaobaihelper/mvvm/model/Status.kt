package cn.xiaobaihome.xiaobaihelper.mvvm.model

data class Status(
    val conncount: Int,
    val connmax: Int,
    val cpuinfo: String,
    val cpuusage: String,
    val ethinfo: String,
    val leases: List<Any>,
    val leases6: List<Any>,
    val loadavg: List<Int>,
    val localtime: String,
    val memcached: String,
    val memory: Memory,
    val swap: Swap,
    val uptime: Int,
    val userinfo: String,
    val wan: Wan,
    val wifinets: List<Any>
)

data class Memory(
    val available: Int,
    val buffered: Int,
    val cached: Int,
    val free: Int,
    val shared: Int,
    val total: Int
)

data class Swap(
    val free: Int,
    val total: Int
)

data class Wan(
    val dns: List<String>,
    val expires: Int,
    val gwaddr: String,
    val ifname: String,
    val ipaddr: String,
    val link: String,
    val netmask: String,
    val proto: String,
    val uptime: Int
)