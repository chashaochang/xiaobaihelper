package cn.xiaobaihome.xiaobaihelper.api

import cn.xiaobaihome.xiaobaihelper.helper.CacheUtil
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class CookieJar : CookieJar {

    val gson = GsonManager.instance.gson

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return arrayListOf()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        if (url.toUrl().path.contains("Action/login")) {//爱快登录
            CacheUtil.set(CacheUtil.IKUAI_COOKIE, gson.toJson(cookies))
        }
    }
}