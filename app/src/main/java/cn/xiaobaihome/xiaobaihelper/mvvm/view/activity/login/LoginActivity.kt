package cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.login

import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import cn.xiaobaihome.xiaobaihelper.databinding.ActivityLoginBinding
import cn.xiaobaihome.xiaobaihelper.mvvm.base.BaseActivity
import cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {

    private val loginViewModel: LoginViewModel by viewModel()
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel.canSee.observe(this, {
            if (it) {
                binding.activityLoginInputPwd.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                binding.activityLoginInputPwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        })
        binding.activityLoginBtnSee.setOnClickListener {
            loginViewModel.canSee.value = !(loginViewModel.canSee.value as Boolean)
            binding.activityLoginInputPwd.setSelection(binding.activityLoginInputPwd.text.length)
        }
        binding.activityLoginInputUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.activityLoginBtnLogin.isEnabled = !p0.isNullOrEmpty() && !binding.activityLoginInputPwd.text.isNullOrEmpty()
            }

        })
        binding.activityLoginInputPwd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.activityLoginBtnLogin.isEnabled = !p0.isNullOrEmpty() && !binding.activityLoginInputUsername.text.isNullOrEmpty()
            }

        })
    }

}