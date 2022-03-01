package cn.xiaobaihome.xiaobaihelper.mvvm.model

import java.net.URLDecoder
import java.text.SimpleDateFormat
import java.util.*

class NewItemParse(newItem: NewItem) {

    var title: String? = newItem.title
    var date: String? = parseDateTime(newItem.date)
    var category: String? = newItem.category
    var authorName: String? = newItem.author_name
    var url: String? = URLDecoder.decode(newItem.url,"UTF-8").replace("https://v.juhe.cn/toutiao/s?id=","")
    var thumbnailPicS: String? = newItem.thumbnail_pic_s

    private fun parseDateTime(date: String?): String {

        val calendar = Calendar.getInstance()
        date?.let {
            val time = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).parse(it)
            time?.let { it2 ->
                calendar.time = it2
            }
        }

        val minute = 1000 * 60
        val hour = minute * 60
        val day = hour * 24
        val result: String
        val now = Date().time
        val diffValue = now - calendar.timeInMillis

        if (diffValue < 0) {
            return "刚刚"
        }
        val dayC = diffValue / day
        val hourC = diffValue / hour
        val minC = diffValue / minute
        result = when {
            dayC > 30 -> {
                SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(calendar.timeInMillis)
            }
            dayC > 1 -> {
                "$dayC 天前"
            }
            dayC == 1L -> {
                "昨天"
            }
            hourC >= 1 -> {
                "$hourC 小时前"
            }
            minC >= 5 -> {
                "$minC 分钟前"
            }
            else -> "刚刚"
        }
        return result

    }
}