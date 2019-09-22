package cn.xiaobaihome.xiaobaihelper.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import cn.xiaobaihome.xiaobaihelper.BR
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.entity.Shortcut
import cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.video.VideoWebViewActivity
import androidx.databinding.BindingAdapter
import android.widget.ImageView
import androidx.annotation.DrawableRes


internal class ShortcutAdapter internal constructor(private var data: List<Shortcut>) : BaseAdapter() {

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val context = parent?.context
        val binding: ViewDataBinding
        binding = if (convertView == null) {
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_gridview_shortcut, parent, false)
        } else {
            DataBindingUtil.getBinding(convertView)!!
        }
        binding.setVariable(BR.shortcut, data[position])
        binding.setVariable(BR.onclick, View.OnClickListener {
            val intent = Intent(context, VideoWebViewActivity::class.java)
            intent.putExtra("url", data[position].url)
            context?.startActivity(intent)
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

    companion object {
        @BindingAdapter("android:src")
        @JvmStatic
        fun setImageViewResource(imageView: ImageView,@DrawableRes resource: Int) {
            imageView.setImageResource(resource)
        }
    }

}