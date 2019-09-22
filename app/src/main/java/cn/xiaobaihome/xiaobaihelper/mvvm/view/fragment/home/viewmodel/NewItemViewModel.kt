package cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.home.viewmodel

import cn.xiaobaihome.xiaobaihelper.mvvm.model.data.NewItem
import java.text.SimpleDateFormat
import java.util.*

class NewItemViewModel(newItem: NewItem) {

    var title: String? = newItem.title
    var date: String? = parseDateTime(newItem.date)
    var category: String? = newItem.category
    var author_name: String? = newItem.author_name
    var url: String? = newItem.url?.replace("http","https")
    var thumbnail_pic_s: String? = newItem.thumbnail_pic_s

    private fun parseDateTime(date: String?): String {

        val calendar = Calendar.getInstance()
        calendar.time = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).parse(date)

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
        if (dayC > 30) {
            result = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(calendar.timeInMillis)
        } else if (dayC > 1) {
            result = "$dayC 天前"
        } else if (dayC == 1L) {
            result = "昨天"
        } else if (hourC >= 1) {
            result = "$hourC 小时前"
        } else if (minC >= 5) {
            result = "$minC 分钟前"
        } else
            result = "刚刚"
        return result

    }
}