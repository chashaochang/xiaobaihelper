package cn.xiaobaihome.xiaobaihelper.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindLayout())
        lifecycle.addObserver(BaseActivityLifecycle(this))
    }

    abstract fun bindLayout():Int

}