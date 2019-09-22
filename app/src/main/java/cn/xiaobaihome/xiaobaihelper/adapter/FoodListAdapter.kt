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
import cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.video.VideoWebViewActivity
import androidx.databinding.BindingAdapter
import android.widget.ImageView
import androidx.annotation.DrawableRes
import cn.xiaobaihome.xiaobaihelper.entity.MapFoodListItem


internal class FoodListAdapter internal constructor(private var data: List<MapFoodListItem>) : BaseAdapter() {

    var selectedItem: Int = -1
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val context = parent?.context
        val binding: ViewDataBinding
        binding = if (convertView == null) {
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_food, parent, false)
        } else {
            DataBindingUtil.getBinding(convertView)!!
        }
        binding.setVariable(BR.item, data[position])
        binding.setVariable(BR.selected,position == selectedItem)
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

}