package cn.xiaobaihome.xiaobaihelper

import android.os.Bundle
import cn.xiaobaihome.xiaobaihelper.base.BaseActivity

class MainActivity : BaseActivity() {



    override fun bindLayout(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}
