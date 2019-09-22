package cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.login

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.base.BaseActivity
import cn.xiaobaihome.xiaobaihelper.databinding.ActivityLoginBinding
import cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        binding.vm = loginViewModel
        binding.activityLoginBtnSee.setOnClickListener {
            loginViewModel.canSee = !loginViewModel.canSee
            binding.activityLoginInputPwd.setSelection(binding.activityLoginInputPwd.text.length)
            binding.vm = loginViewModel
        }
        binding.activityLoginInputUsername.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.activityLoginBtnLogin.isEnabled = !p0.isNullOrEmpty() && !binding.activityLoginInputPwd.text.isNullOrEmpty()
            }

        })
        binding.activityLoginInputPwd.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.activityLoginBtnLogin.isEnabled = !p0.isNullOrEmpty() && !binding.activityLoginInputUsername.text.isNullOrEmpty()
            }

        })
    }

    override fun loadData(isRefresh: Boolean) {

    }

    override fun onClick(v: View?) {

    }
}