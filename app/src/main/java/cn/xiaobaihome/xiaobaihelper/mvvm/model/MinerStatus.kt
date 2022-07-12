package cn.xiaobaihome.xiaobaihelper.mvvm.model

data class MinerStatus(
    val miner: Miner = Miner(),
    val reboot_times: Int = 0,
    val start_time: Int = 0,
    val stratum: Stratum = Stratum(),
    val version: String = ""
) {
    data class Miner(
        val devices: List<Device> = listOf(),
        val total_hashrate: String = "",
        val total_hashrate2: String = "",
        val total_hashrate2_raw: Int = 0,
        val total_hashrate_raw: Double = 0.0,
        val total_power_consume: Int = 0
    ) {
        data class Device(
            val accepted_shares: Int = 0,
            val core_clock: Int = 0,
            val core_utilization: Int = 0,
            val fan: Int = 0,
            val hashrate: String = "",
            val hashrate2: String = "",
            val hashrate2_raw: Int = 0,
            val hashrate_raw: Double = 0.0,
            val id: Int = 0,
            val info: String = "",
            val invalid_shares: Int = 0,
            val memTemperature: Int = 0,
            val mem_clock: Int = 0,
            val mem_utilization: Int = 0,
            val pci_bus_id: Int = 0,
            val power: Int = 0,
            val rejected_shares: Int = 0,
            val temperature: Int = 0
        )
    }

    data class Stratum(
        val accepted_shares: Int = 0,
        val algorithm: String = "",
        val difficulty: String = "",
        val dual_mine: Boolean = false,
        val invalid_shares: Int = 0,
        val latency: Int = 0,
        val pool_hashrate_10m: String = "",
        val pool_hashrate_24h: String = "",
        val pool_hashrate_4h: String = "",
        val rejected_shares: Int = 0,
        val url: String = "",
        val use_ssl: Boolean = false,
        val user: String = ""
    )
}