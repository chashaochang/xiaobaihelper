package cn.xiaobaihome.xiaobaihelper.mvvm.view.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.databinding.FragmentHomeBinding
import cn.xiaobaihome.xiaobaihelper.entity.Shortcut
import cn.xiaobaihome.xiaobaihelper.helper.extens.getStatusBarHeight
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseFragment
import cn.xiaobaihome.xiaobaihelper.mvvm.view.schedule.ScheduleActivity
import cn.xiaobaihome.xiaobaihelper.mvvm.view.webview.WebViewActivity
import com.google.zxing.client.android.CaptureActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    lateinit var binding: FragmentHomeBinding

    private val homeViewModel: HomeViewModel by viewModels()

    private var adapter: NewsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.homeFragmentToolbar.setPadding(0, getStatusBarHeight(requireContext()),0,0)
        binding.homeFragmentScrollView.setOnScrollChangeListener { _, _, _, _, p4 ->
            if (p4 <= 100) {
                binding.homeFragmentToolbarTitle.text = ""
                val array =
                    activity?.theme?.obtainStyledAttributes(intArrayOf(R.color.main_bg_color))
                val bgColor = array?.getColor(0, 0xffffff)
                bgColor?.let { binding.homeFragmentToolbar.setBackgroundColor(it) }
            } else {
                binding.homeFragmentToolbarTitle.text = "小白助手"
                binding.homeFragmentToolbar.background =
                    context?.let { ContextCompat.getDrawable(it, R.drawable.cell_border_bottom_ed) }
            }
        }
        adapter = NewsAdapter(requireActivity(), ArrayList())
        binding.lvNews.adapter = adapter

        val list = ArrayList<Shortcut>()

        val shortcutVideo = Shortcut(R.mipmap.video, "小程序1", "gh_000000000001")
        val shortcutFood = Shortcut(R.mipmap.food, "小程序2", "gh_000000000002")
        val shortcutSchedule = Shortcut(R.mipmap.schedule, "小程序3", "gh_000000000003")
        val shortcutSchedule1 = Shortcut(R.mipmap.schedule, "小程序4", "gh_000000000004")
        val shortcutSchedule2 = Shortcut(R.mipmap.schedule, "小程序5", "gh_000000000005")
        val shortcutSchedule3 = Shortcut(R.mipmap.schedule, "小程序6", "gh_000000000006")
        list.add(shortcutVideo)
        list.add(shortcutFood)
        list.add(shortcutSchedule)
        list.add(shortcutSchedule1)
        list.add(shortcutSchedule2)
        list.add(shortcutSchedule3)
        binding.homeFragmentGridView.adapter = HomeGridViewAdapter(requireActivity(), list)
        binding.homeFragmentGridView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, p2, _ ->
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

        binding.homeFragmentToolbarScan.setOnClickListener {
            requestCameraPermission()
        }
        homeViewModel.news.observe(viewLifecycleOwner){
            adapter?.data = it
            adapter?.notifyDataSetChanged()
        }
        launch {
            homeViewModel.loadData()
        }
        return binding.root
    }

    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                val intent = Intent(context, CaptureActivity::class.java)
                scanLauncher.launch(intent)
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }

    private val scanLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val data = it.data
            val result = data?.getStringExtra("result")
            if (result?.contains("http")!!) {
                val intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra("url", result)
                startActivity(intent)
            }
        }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                val intent = Intent(context, CaptureActivity::class.java)
                scanLauncher.launch(intent)
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)-> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                alert("扫码需要开启相机权限", "确定") { _, _ -> requestCameraPermission() }
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }

    }

}