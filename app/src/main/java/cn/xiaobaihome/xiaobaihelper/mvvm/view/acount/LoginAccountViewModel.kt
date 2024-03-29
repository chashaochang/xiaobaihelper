package cn.xiaobaihome.xiaobaihelper.mvvm.view.acount

import cn.xiaobaihome.xiaobaihelper.api.ApiService
import cn.xiaobaihome.xiaobaihelper.api.IKuaiApiService
import cn.xiaobaihome.xiaobaihelper.api.MinerApiService
import cn.xiaobaihome.xiaobaihelper.api.OpenWrtApiService
import cn.xiaobaihome.xiaobaihelper.bean.IkuaiLoginBean
import cn.xiaobaihome.xiaobaihelper.helper.AppData
import cn.xiaobaihome.xiaobaihelper.helper.CacheUtil
import cn.xiaobaihome.xiaobaihelper.helper.md5
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginAccountViewModel @Inject constructor(val apiService: ApiService) : BaseViewModel() {

    var type: MutableStateFlow<Int> = MutableStateFlow(-1)

    suspend fun testLogin(
        protocol: String,
        address: String,
        port: String,
        username: String,
        pwd: String,
        result: (Boolean, String?) -> Unit
    ) {
        when (type.value) {
            0 -> {
                //ikuai
                ikuaiLogin(protocol, address, port, username, pwd, result)
            }
            1 -> {
                //openwrt
                openwrtLogin(protocol, address, port, username, pwd, result)
            }
            2 -> {
                //unraid
            }
            3 -> {
                //nbminer
                minerLogin(protocol, address, port, result)
            }
        }
    }

    private suspend fun ikuaiLogin(
        protocol: String,
        address: String,
        port: String,
        username: String,
        pwd: String,
        result: (Boolean, String?) -> Unit
    ) {
        IKuaiApiService.BASE_URL = "${protocol}://${address}:${port}"
        val ikuaiApiService = IKuaiApiService.create()
        try {
            val password = md5(pwd)
            launch2(
                ikuaiApiService.login(
                    IkuaiLoginBean(username, password, password, true)
                )
            ) {
                if (it != null) {
                    when (it.result) {
                        10000 -> {
                            result(true, null)
                            CacheUtil.set(CacheUtil.IKUAI_PROTOCOL, protocol)
                            CacheUtil.set(CacheUtil.IKUAI_ADDRESS, address)
                            CacheUtil.set(CacheUtil.IKUAI_PORT, port)
                            CacheUtil.set(CacheUtil.IKUAI_USERNAME, username)
                            CacheUtil.set(CacheUtil.IKUAI_PWD, pwd)
                            AppData.isIKuaiLogin.value = true
                        }
                        10001 -> {
                            result(false, it.errMsg)
                        }
                        else -> {
                            result(false, "登录失败，code:${it.result},msg:${it.errMsg}")
                        }
                    }
                } else {
                    result(false, null)
                }
            }
        } catch (e: Exception) {
            result(false, e.toString())
        }
    }

    private suspend fun openwrtLogin(
        protocol: String,
        address: String,
        port: String,
        username: String,
        pwd: String,
        result: (Boolean, String?) -> Unit
    ) {
        OpenWrtApiService.BASE_URL = "${protocol}://${address}:${port}"
        val openwrtApiService = OpenWrtApiService.create()
        try {
            launch2(
                openwrtApiService.login(username, pwd)
            ) {
//                if (it != null && it.contains("自动刷新")) {
//                    result(true, null)
//                }
            }
        } catch (e: Exception) {
            if (e is HttpException) {
                if (e.code() == 302) {
                    result(true, null)
                    CacheUtil.set(CacheUtil.OPENWRT_PROTOCOL, protocol)
                    CacheUtil.set(CacheUtil.OPENWRT_ADDRESS, address)
                    CacheUtil.set(CacheUtil.OPENWRT_PORT, port)
                    CacheUtil.set(CacheUtil.OPENWRT_USERNAME, username)
                    CacheUtil.set(CacheUtil.OPENWRT_PWD, pwd)
                    AppData.isOpenWrtLogin.value = true
                } else if (e.code() == 403) {
                    result(false, "无效的用户名和/或密码！请重试。")
                }
            } else {
                result(false, e.toString())
            }
        }
    }

    private suspend fun minerLogin(
        protocol: String,
        address: String,
        port: String,
        result: (Boolean, String?) -> Unit
    ) {
        MinerApiService.BASE_URL = "${protocol}://${address}:${port}"
        val minerApiService = MinerApiService.create()
        try {
            launch2(minerApiService.getMinerStatus()) {
                if (it != null && it.version.isNotBlank()) {
                    result(true, null)
                    CacheUtil.set(CacheUtil.MINER_PROTOCOL, protocol)
                    CacheUtil.set(CacheUtil.MINER_ADDRESS, address)
                    CacheUtil.set(CacheUtil.MINER_PORT, port)
                    AppData.isMinerLogin.value = true
                } else {
                    result(false, null)
                }
            }
        } catch (e: Exception) {
            result(false, e.toString())
        }
    }
}