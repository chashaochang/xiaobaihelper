package cn.xiaobaihome.xiaobaihelper.mvvm.view.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import cn.xiaobaihome.xiaobaihelper.databinding.ItemNewsBinding
import cn.xiaobaihome.xiaobaihelper.mvvm.model.NewItemParse
import cn.xiaobaihome.xiaobaihelper.mvvm.view.webview.WebViewActivity
import com.bumptech.glide.Glide

class NewsAdapter constructor(private var context: Context, var data: List<NewItemParse>) : BaseAdapter() {

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemNewsBinding
        if (convertView == null) {
            binding = ItemNewsBinding.inflate((context as Activity).layoutInflater)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ItemNewsBinding
        }
        val item = data[position]
        Glide.with(context)
                .load(item.thumbnailPicS)
                .centerCrop()
                .into(binding.itemNewsPic)
        binding.itemNewsTitle.text = item.title
        binding.itemNewsCategory.text = item.category
        binding.itemNewsAuthorName.text = if (item.authorName?.length!! > 9) item.authorName!!.substring(0, 8).plus("...") else item.authorName
        binding.itemNewsDate.text = item.date
        binding.root.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("url", item.url)
            context.startActivity(intent)
        }
        return binding.root
    }


}