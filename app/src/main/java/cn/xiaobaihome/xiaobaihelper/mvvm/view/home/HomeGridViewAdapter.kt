package cn.xiaobaihome.xiaobaihelper.mvvm.view.home

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import cn.xiaobaihome.xiaobaihelper.databinding.ItemHomeGridviewShortcutBinding
import cn.xiaobaihome.xiaobaihelper.entity.Shortcut
import cn.xiaobaihome.xiaobaihelper.miniprogram.MiniProgramManager

internal class HomeGridViewAdapter internal constructor(
    private var context: Context,
    private var data: List<Shortcut>
) : BaseAdapter() {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemHomeGridviewShortcutBinding
        if (convertView == null) {
            binding = ItemHomeGridviewShortcutBinding.inflate((context as Activity).layoutInflater)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ItemHomeGridviewShortcutBinding
        }
        val shortcut = data[position]
        binding.itemHomeGridviewShortcutImg.setImageResource(shortcut.imgResId)
        binding.itemHomeGridviewShortcutText.text = shortcut.title
        binding.itemHomeGridviewShortcutContainer.setOnClickListener {
            MiniProgramManager.openMiniProgram(
                context,
                shortcut.url,
                shortcut.title,
                shortcut.imgResId
            )
        }
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