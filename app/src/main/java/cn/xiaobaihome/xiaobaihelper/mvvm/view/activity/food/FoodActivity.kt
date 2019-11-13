package cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.food

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.adapter.FoodListAdapter
import cn.xiaobaihome.xiaobaihelper.base.BaseActivity
import cn.xiaobaihome.xiaobaihelper.databinding.ActivityFoodBinding
import cn.xiaobaihome.xiaobaihelper.entity.MapFoodListItem
import cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.random.RandomActivity
import com.baidu.location.BDLocation
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.LocationClientOption
import com.baidu.location.LocationClient
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.poi.*
import com.tbruyelle.rxpermissions2.RxPermissions
import com.baidu.mapapi.map.MarkerOptions
import com.baidu.mapapi.map.BitmapDescriptorFactory
import com.baidu.mapapi.search.core.PoiInfo
import com.google.gson.Gson

class FoodActivity : BaseActivity<ActivityFoodBinding>() {

    private var mapView: MapView? = null
    private var baiduMap: BaiduMap? = null
    private var locationClient: LocationClient? = null
    private var rxPermission: RxPermissions? = null
    private var poiSearch: PoiSearch? = null
    private var foodListAdapter: FoodListAdapter? = null
    private var foodList: MutableList<MapFoodListItem> = ArrayList()
    private var markerList: MutableList<Marker> = ArrayList()
    private var selectMarker: Int = -1

    override fun getLayoutId(): Int {
        return R.layout.activity_food
    }


    override fun initView() {
        checkPermission()
        initMapView()
        initListView()
        binding.activityFoodHelpBtn.setOnClickListener {
            if (foodList.size > 0) {
                val intent = Intent(this, RandomActivity::class.java)
                intent.putExtra("list", Gson().toJson(foodList))
                startActivity(intent)
            }
        }
        binding.activityFoodBack.setOnClickListener {
            finish()
        }
    }

    private fun initListView() {
        foodListAdapter = FoodListAdapter(foodList)
        binding.activityFoodListview.adapter = foodListAdapter
        binding.activityFoodListview.setOnItemClickListener { _, _, i, _ ->
            //如果有已选中的先把图标换回去
            if (selectMarker >= 0) {
                val lastSelectedMarker = markerList[selectMarker]
                val bitmap = getMarkerIcon(false)
                lastSelectedMarker.icon = bitmap
                bitmap.recycle()
            }
            selectMarker = i
            val marker = markerList[i]
            val bitmap = getMarkerIcon(true)
            marker.icon = bitmap
            marker.setToTop()
            bitmap.recycle()
        }
    }

    private fun initMapView() {
        mapView = binding.activityFoodMapview

        // 不显示地图缩放控件（按钮控制栏）
        mapView?.showZoomControls(false)

        baiduMap = mapView?.map
        baiduMap?.isMyLocationEnabled = true
        poiSearch = PoiSearch.newInstance()

        //定位初始化
        locationClient = LocationClient(this)

        //通过LocationClientOption设置LocationClient相关参数
        val option = LocationClientOption()
        option.isOpenGps = true // 打开gps
        option.setCoorType("bd09ll") // 设置坐标类型
        option.setScanSpan(500)

        //设置locationClientOption
        locationClient?.locOption = option

        //注册LocationListener监听器
        val myLocationListener = MyLocationListener()
        locationClient?.registerLocationListener(myLocationListener)
        //开启地图定位图层
        locationClient?.start()

        poiSearch?.setOnGetPoiSearchResultListener(poiSearchResultListener)
    }

    inner class MyLocationListener : BDAbstractLocationListener() {
        override fun onReceiveLocation(location: BDLocation?) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mapView == null) {
                return
            }
            val locData = MyLocationData.Builder()
                    .accuracy(location.radius)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.direction).latitude(location.latitude)
                    .longitude(location.longitude).build()
            baiduMap?.setMyLocationData(locData)
            baiduMap?.animateMapStatus(MapStatusUpdateFactory.newLatLngZoom(LatLng(location.latitude - 0.003, location.longitude), 18f))
            poiSearch?.searchNearby(PoiNearbySearchOption()
                    .location(LatLng(location.latitude, location.longitude))
                    .radius(1000)
                    .keyword("美食") //必填
                    .pageCapacity(20)
                    .pageNum(0))
            locationClient?.stop()
        }
    }

    private var poiSearchResultListener: OnGetPoiSearchResultListener = object : OnGetPoiSearchResultListener {
        override fun onGetPoiResult(poiResult: PoiResult) {
            println(poiResult.allPoi)
            if(poiResult.allPoi == null){
                return
            }
            foodList.clear()
            for (poi in poiResult.allPoi) {
                foodList.add(MapFoodListItem(poi))
            }
            foodListAdapter?.notifyDataSetChanged()
            addMarkers(poiResult.allPoi)
        }

        override fun onGetPoiDetailResult(poiDetailSearchResult: PoiDetailSearchResult) {

        }

        override fun onGetPoiIndoorResult(poiIndoorResult: PoiIndoorResult) {

        }

        //废弃
        override fun onGetPoiDetailResult(poiDetailResult: PoiDetailResult) {

        }
    }

    @SuppressLint("AutoDispose", "CheckResult")
    private fun checkPermission() {
        rxPermission = RxPermissions(this)
        rxPermission!!
                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe { permission ->
//                    if (permission.granted) {
//                        // `permission.name` is granted !
//                    } else if (permission.shouldShowRequestPermissionRationale) {
//                        // Denied permission without ask never again
//                    } else {
//                        // Denied permission with ask never again
//                        // Need to go to the settings
//                    }
                }
    }

    private fun addMarkers(poiInfos: MutableList<PoiInfo>) {
        baiduMap?.clear()
        for (poi in poiInfos) {
            //定义Maker坐标点
            //构建Marker图标
            val bitmap = getMarkerIcon(false)
            //构建MarkerOption，用于在地图上添加Marker
            val option = MarkerOptions()
                    .position(poi.location)
                    .icon(bitmap)
            //在地图上添加Marker，并显示
            val marker = baiduMap?.addOverlay(option)
            markerList.add(marker as Marker)
            bitmap.recycle()
        }

    }

    private fun getMarkerIcon(select: Boolean): BitmapDescriptor {
        val res = if (select) R.mipmap.marker_food_checked else R.mipmap.marker_food_unchecked
        return BitmapDescriptorFactory.fromResource(res)
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView?.onPause()
    }

    override fun onDestroy() {
        locationClient?.stop()
        baiduMap?.isMyLocationEnabled = false
        poiSearch?.destroy()
        mapView?.onDestroy()
        mapView = null
        super.onDestroy()
    }

}