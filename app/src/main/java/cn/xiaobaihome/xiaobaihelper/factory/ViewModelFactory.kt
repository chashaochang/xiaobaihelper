package cn.xiaobaihome.xiaobaihelper.factory

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(val application: Application, val argument: Bundle) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val params = arrayOf<Class<*>>(Application::class.java, Bundle::class.java)
        return modelClass.getConstructor(params[0], params[1]).newInstance(application, argument)
    }
}