package cn.xiaobaihome.xiaobaihelper.ui.adapter

import android.annotation.SuppressLint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import cn.xiaobaihome.xiaobaihelper.R
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
//        val binding: ViewDataBinding
//        binding = if (convertView == null) {
//            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_food, parent, false)
//        } else {
//            DataBindingUtil.getBinding(convertView)!!
//        }
//        binding.setVariable(BR.item, data[position])
//        binding.setVariable(BR.selected,position == selectedItem)
//        return binding.root
        return convertView!!
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