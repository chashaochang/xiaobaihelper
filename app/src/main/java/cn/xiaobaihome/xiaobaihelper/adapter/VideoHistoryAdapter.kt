package cn.xiaobaihome.xiaobaihelper.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import cn.xiaobaihome.xiaobaihelper.BR
import cn.xiaobaihome.xiaobaihelper.R
import androidx.databinding.BindingAdapter
import cn.xiaobaihome.xiaobaihelper.entity.VideoHistoryItem
import cn.xiaobaihome.xiaobaihelper.helper.addVideoHistory
import cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.video.VideoWebViewActivity
import cn.xiaobaihome.xiaobaihelper.widget.GlideRoundTransform
import cn.xiaobaihome.xiaobaihelper.widget.RoundImageView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

internal class VideoHistoryAdapter internal constructor(private var data: List<VideoHistoryItem>) : BaseAdapter() {

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val context = parent?.context
        val binding: ViewDataBinding
        binding = if (convertView == null) {
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_video_history, parent, false)
        } else {
            DataBindingUtil.getBinding(convertView)!!
        }
        val videoHistoryItem = data[position]
        videoHistoryItem.timeShow = parseDateTime(videoHistoryItem.time)
        binding.setVariable(BR.historyItem, videoHistoryItem)
        binding.setVariable(BR.onclick, View.OnClickListener {
            videoHistoryItem.time = Date().time
            addVideoHistory(context!!,videoHistoryItem)
            val intent = Intent(context, VideoWebViewActivity::class.java)
            intent.putExtra("url", videoHistoryItem.url)
            context.startActivity(intent)
        })
        return binding.root
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

    private fun parseDateTime(date: Long): String {

        val calendar = Calendar.getInstance()
        calendar.time = Date(date)

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

    companion object {

        @BindingAdapter("android:src")
        @JvmStatic
        fun setImageViewResource(imageView: RoundImageView,resource: String) {
            Glide.with(imageView.context)
                    .load(resource)
                    .transform(GlideRoundTransform(imageView.context, 10))
                    .into(imageView)
        }
    }

}