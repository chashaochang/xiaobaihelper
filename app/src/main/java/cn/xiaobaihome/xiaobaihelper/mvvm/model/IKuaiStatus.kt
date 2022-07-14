package cn.xiaobaihome.xiaobaihelper.mvvm.model

data class IKuaiStatus(
    val ac_status: AcStatus = AcStatus(),
    val sysstat: Sysstat = Sysstat()
) {
    data class AcStatus(
        val ap_count: Int = 0,
        val ap_online: Int = 0
    )

    data class Sysstat(
        val cpu: List<String> = listOf(),
        val cputemp: List<Int> = listOf(),
        val freq: List<String> = listOf(),
        val gwid: String = "",
        val hostname: String = "",
        val link_status: Int = 0,
        val memory: Memory = Memory(),
        val online_user: OnlineUser = OnlineUser(),
        val stream: Stream = Stream(),
        val uptime: Int = 0,
        val verinfo: Verinfo = Verinfo()
    ) {
        data class Memory(
            val available: Int = 0,
            val buffers: Int = 0,
            val cached: Int = 0,
            val free: Int = 0,
            val total: Int = 0,
            val used: String = ""
        )

        data class OnlineUser(
            val count: Int = 0,
            val count_2g: Int = 0,
            val count_5g: Int = 0,
            val count_wired: Int = 0,
            val count_wireless: Int = 0
        )

        data class Stream(
            val connect_num: Int = 0,
            val download: Int = 0,
            val total_down: Long = 0,
            val total_up: Long = 0,
            val upload: Int = 0
        )

        data class Verinfo(
            val arch: String = "",
            val build_date: Long = 0,
            val is_enterprise: Int = 0,
            val modelname: String = "",
            val support_i18n: Int = 0,
            val support_lcd: Int = 0,
            val sysbit: String = "",
            val verflags: String = "",
            val version: String = "",
            val verstring: String = ""
        )
    }
}