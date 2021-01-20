package cn.xiaobaihome.xiaobaihelper.mvvm.view.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.databinding.ItemNewsBinding
import cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.webview.WebViewActivity
import cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.home.viewmodel.NewItemViewModel
import com.bumptech.glide.Glide

class NewsAdapter constructor(private var context: Context, var data: List<NewItemViewModel>) : BaseAdapter() {

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
                .load(item.thumbnail_pic_s)
                .centerCrop()
                .into(binding.itemNewsPic)
        binding.itemNewsTitle.text = item.title
        binding.itemNewsCategory.text = item.category
        binding.itemNewsAuthorName.text = if (item.author_name?.length!! > 9) item.author_name!!.substring(0, 8).plus("...") else item.author_name
        binding.itemNewsDate.text = item.date
        binding.root.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_DOCUMENT
//        intent.flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            intent.putExtra("url", item.url)
            context.startActivity(intent)
            //val activity = context as Activity
            //activity.overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out)
        }
        return binding.root
    }


}