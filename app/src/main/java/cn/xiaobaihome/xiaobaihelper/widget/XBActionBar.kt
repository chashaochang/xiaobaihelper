package cn.xiaobaihome.xiaobaihelper.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.Toolbar

class XBActionBar : Toolbar {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        //init(context)要在retrieveAttributes(attrs)前调用
        //因为属性赋值，会直接赋值到控件上去。如:
        //调用label = ""时，相当于调用了label的set方法。
        init(context)
    }

    private fun init(context: Context) {

    }
}