package cn.xiaobaihome.xiaobaihelper.mvvm.view.fragment.home.viewmodel

import cn.xiaobaihome.xiaobaihelper.mvvm.model.data.NewItem

class NewItemViewModel(newItem: NewItem) {
    var title: String? = newItem.title
    var date: String? = newItem.date
    var category: String? = newItem.category
    var author_name: String? = newItem.author_name
    var url: String? = newItem.url
    var thumbnail_pic_s: String? = newItem.thumbnail_pic_s
    var thumbnail_pic_s02: String? = newItem.thumbnail_pic_s02
    var thumbnail_pic_s03: String? = newItem.thumbnail_pic_s03
}