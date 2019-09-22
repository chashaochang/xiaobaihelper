package cn.xiaobaihome.xiaobaihelper.entity

import com.baidu.mapapi.search.core.PoiInfo

data class MapFoodListItem constructor(var poiInfo: PoiInfo) {

    var address = poiInfo.address
    var name = poiInfo.name
    var location = poiInfo.location
    var phoneNum = poiInfo.phoneNum
}