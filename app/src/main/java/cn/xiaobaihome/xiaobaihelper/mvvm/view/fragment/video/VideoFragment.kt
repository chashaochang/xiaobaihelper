package cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.video

import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.adapter.ShortcutAdapter
import cn.xiaobaihome.xiaobaihelper.base.BaseFragment
import cn.xiaobaihome.xiaobaihelper.databinding.FragmentVideoBinding
import cn.xiaobaihome.xiaobaihelper.entity.Shortcut
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
}