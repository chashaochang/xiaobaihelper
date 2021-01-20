package cn.xiaobaihome.xiaobaihelper.mvvm.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.databinding.FragmentHomeBinding
import cn.xiaobaihome.xiaobaihelper.entity.Shortcut
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseFragment
import cn.xiaobaihome.xiaobaihelper.mvvm.model.data.JuHeResp
import cn.xiaobaihome.xiaobaihelper.mvvm.model.data.RespResult
import cn.xiaobaihome.xiaobaihelper.mvvm.model.data.VersionInfo
import cn.xiaobaihome.xiaobaihelper.mvvm.model.remote.Utils
import cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.schedule.ScheduleActivity
import cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.webview.WebViewActivity
import cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.home.viewmodel.HomeViewModel
import cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.home.viewmodel.NewItemViewModel
import com.google.zxing.client.android.CaptureActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : BaseFragment() {

    lateinit var binding: FragmentHomeBinding

    private val homeViewModel: HomeViewModel by viewModel()

    private var adapter: NewsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.homeFragmentScrollView.setOnScrollChangeListener { _, _, _, _, p4 ->
            if (p4 <= 100) {
                binding.homeFragmentToolbarTitle.text = ""
                val array = activity?.theme?.obtainStyledAttributes(intArrayOf(R.color.main_bg_color))
                val bgColor = array?.getColor(0, 0xffffff)
                bgColor?.let { binding.homeFragmentToolbar.setBackgroundColor(it) }
            } else {
                binding.homeFragmentToolbarTitle.text = "小白助手"
                binding.homeFragmentToolbar.background = context?.let { ContextCompat.getDrawable(it, R.drawable.cell_border_bottom_ed) }
            }
        }
        adapter = NewsAdapter(requireContext(),ArrayList())
        binding.lvNews.adapter = adapter

        val list = ArrayList<Shortcut>()

        val shortcutVideo = Shortcut(R.mipmap.video, "影视", "video")
        val shortcutFood = Shortcut(R.mipmap.food, "美食", "food")
        val shortcutSchedule = Shortcut(R.mipmap.schedule, "日程", "schedule")
        list.add(shortcutVideo)
        list.add(shortcutFood)
        list.add(shortcutSchedule)
        binding.homeFragmentGridView.adapter = HomeGridViewAdapter(context!!, list)
        binding.homeFragmentGridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, p2, _ ->
            when (p2) {
                0 -> {
                    val guidanceActivity = activity as HomeActivity
                    guidanceActivity.changeBottomIndex(2)
                }
                1 -> {
                    //requestMapPermission()
                }
                2 -> {
                    startActivity(Intent(context, ScheduleActivity::class.java))
                }
            }
        }

        homeViewModel.getVersion(object : RespResult<VersionInfo> {

            override fun onSuccess(t: VersionInfo) {
                if (t.versionCode > getVersion()) {
                    alert(t.memo.toString(), "更新") { _, _ ->
                        context?.let { it1 -> t.address?.let { it2 -> Utils.downLoadNew(it1, it2) } }
                    }
                }
            }

            override fun onError(msg: String) {
                //alert(msg)
            }

        })

        binding.homeFragmentToolbarScan.setOnClickListener {
            requestCameraPermission()
        }
        homeViewModel.loadData(object : RespResult<JuHeResp> {
            override fun onSuccess(t: JuHeResp) {
                t.result?.data1?.map(::NewItemViewModel)?.let {
                    adapter?.data = it
                    adapter?.notifyDataSetChanged()
                }
            }

            override fun onError(msg: String) {
                alert(msg)
            }

        })
        return binding.root
    }

    private fun requestCameraPermission() {

//        val a = rxPermissions?.request(Manifest.permission.CAMERA)
//                ?.subscribe {
//                    // will emit 1 Permission object
//                    if (it) {
        startActivityForResult(Intent(context, CaptureActivity::class.java), 666)
//                    } else {
//                        alert("扫码需要开启相机权限", "确定") { _, _ -> requestCameraPermission() }
//                    }
//                }
//        compositeDisposable?.add(a!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 666 && resultCode == 666) {
            val result = data?.getStringExtra("result")
            if (result?.contains("http")!!) {
                val intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra("url", result)
                startActivity(intent)
            }
        }
    }

}