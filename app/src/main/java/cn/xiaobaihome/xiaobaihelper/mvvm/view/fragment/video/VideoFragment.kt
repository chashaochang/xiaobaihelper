package cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.video

import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.adapter.ShortcutAdapter
import cn.xiaobaihome.xiaobaihelper.adapter.VideoHistoryAdapter
import cn.xiaobaihome.xiaobaihelper.base.BaseFragment
import cn.xiaobaihome.xiaobaihelper.databinding.FragmentVideoBinding
import cn.xiaobaihome.xiaobaihelper.entity.Shortcut
import cn.xiaobaihome.xiaobaihelper.entity.VideoHistoryItem
import cn.xiaobaihome.xiaobaihelper.helper.getData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

class VideoFragment : BaseFragment<FragmentVideoBinding>() {

    override fun initView() {
        binding.videoFragmentScrollView.setOnScrollChangeListener { _, _, _, _, p4 ->
            if (p4 <= 100) {
                binding.videoFragmentToolbar.text = ""
            } else {
                binding.videoFragmentToolbar.text = "影视"
            }
        }

        initWebsites()
        displayHistory()
    }

    private fun displayHistory() {
        val historyListStr = context?.let { getData(it, "video_history") }
        println(historyListStr)
        val historyList: MutableList<VideoHistoryItem>
        if (historyListStr != null && historyListStr.isNotEmpty()) {
            historyList = Gson().fromJson(historyListStr, object : TypeToken<MutableList<VideoHistoryItem>>() {}.type)
//            if (historyList.size > 5) {//只显示最新的3条
//                historyList = historyList.asReversed()
//            }
            binding.videoFragmentHistoryList.adapter = VideoHistoryAdapter(historyList.asReversed())
        }

    }

    private fun initWebsites() {
        val list = ArrayList<Shortcut>()
        val shortcutAqy = Shortcut(R.mipmap.aiqiyi, "爱奇艺", "https://m.iqiyi.com/")
        val shortcutTx = Shortcut(R.mipmap.txsp, "腾讯视频", "https://v.qq.com")
        val shortcutBilibili = Shortcut(R.mipmap.bilibili, "哔哩哔哩", "https://m.bilibili.com")
        val shortcutYouKu = Shortcut(R.mipmap.youku, "优酷", "https://m.youku.com")
        list.add(shortcutAqy)
        list.add(shortcutTx)
        list.add(shortcutBilibili)
        list.add(shortcutYouKu)
        binding.videoFragmentGridview.adapter = ShortcutAdapter(list)
    }

    override fun getLayoutId(): Int = R.layout.fragment_video

    override fun loadData(isRefresh: Boolean) {

    }

    override fun onResume() {
        super.onResume()
        displayHistory()
    }
}