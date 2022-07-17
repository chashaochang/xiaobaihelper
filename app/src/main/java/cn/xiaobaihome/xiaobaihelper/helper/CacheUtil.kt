package cn.xiaobaihome.xiaobaihelper.helper

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

object CacheUtil {

    val CACHE_APP_VERSION = stringPreferencesKey("CACHE_APP_VERSION")
    val MINER_PROTOCOL = stringPreferencesKey("MINER_PROTOCOL")
    val MINER_ADDRESS = stringPreferencesKey("MINER_ADDRESS")
    val MINER_PORT = stringPreferencesKey("MINER_PORT")

    val IKUAI_PROTOCOL = stringPreferencesKey("IKUAI_PROTOCOL")
    val IKUAI_ADDRESS = stringPreferencesKey("IKUAI_ADDRESS")
    val IKUAI_PORT = stringPreferencesKey("IKUAI_PORT")
    val IKUAI_USERNAME = stringPreferencesKey("IKUAI_USERNAME")
    val IKUAI_PWD = stringPreferencesKey("IKUAI_PWD")
    val IKUAI_COOKIE = stringPreferencesKey("IKUAI_COOKIE")

    val OPENWRT_PROTOCOL = stringPreferencesKey("OPENWRT_PROTOCOL")
    val OPENWRT_ADDRESS = stringPreferencesKey("OPENWRT_ADDRESS")
    val OPENWRT_PORT = stringPreferencesKey("OPENWRT_PORT")
    val OPENWRT_USERNAME = stringPreferencesKey("OPENWRT_USERNAME")
    val OPENWRT_PWD = stringPreferencesKey("OPENWRT_PWD")
    val OPENWRT_COOKIE = stringPreferencesKey("OPENWRT_COOKIE")

    private const val ACCOUNT_PREFERENCES_NAME = "account_preferences"

    private val Context.dataStore by preferencesDataStore(
        name = ACCOUNT_PREFERENCES_NAME
    )

    private lateinit var context: Context
    fun init(context: Context) {
        this.context = context
    }

    fun <T> set(key: Preferences.Key<T>, value: T) {
        runBlocking {
            context.dataStore.edit {
                it[key] = value
            }
        }
    }

    fun <T> get(key: Preferences.Key<T>): T? {
        return runBlocking {
            context.dataStore.data.map { it[key] }.first()
        }
    }
}