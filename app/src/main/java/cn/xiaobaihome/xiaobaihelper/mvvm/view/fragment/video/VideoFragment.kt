package cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.video

import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.adapter.ShortcutAdapter
import cn.xiaobaihome.xiaobaihelper.base.BaseFragment
import cn.xiaobaihome.xiaobaihelper.databinding.FragmentVideoBinding
import cn.xiaobaihome.xiaobaihelper.entity.Shortcut
import java.util.ArrayList

class VideoFragment : BaseFragment<FragmentVideoBinding>() {

    override fun initView() {
        val list = ArrayList<Shortcut>()
        val shortcutAqy = Shortcut(R.mipmap.aiqiyi, "爱奇艺", "https://m.iqiyi.com/")
        val shortcutTx = Shortcut(R.mipmap.txsp, "腾讯视频", "https://v.qq.com")
        val shortcutPptv = Shortcut(R.mipmap.bilibili, "哔哩哔哩", "https://m.bilibili.com")
        list.add(shortcutAqy)
        list.add(shortcutTx)
        list.add(shortcutPptv)
        binding.videoFragmentGridview.adapter = ShortcutAdapter(list)
    }

    override fun getLayoutId(): Int = R.layout.fragment_video

    override fun loadData(isRefresh: Boolean) {

    }
}