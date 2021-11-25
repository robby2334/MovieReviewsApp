package com.dev.divig.moviereviewsapp.ui.login

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseActivity
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.UserEntity
import com.dev.divig.moviereviewsapp.data.network.model.request.auth.AuthRequest
import com.dev.divig.moviereviewsapp.databinding.ActivityLoginBinding
import com.dev.divig.moviereviewsapp.ui.login.adapter.LoginAdapter
import com.dev.divig.moviereviewsapp.ui.main.MainActivity
import com.dev.divig.moviereviewsapp.utils.Constant
import com.dev.divig.moviereviewsapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity :
    BaseActivity<ActivityLoginBinding, LoginViewModel>(ActivityLoginBinding::inflate),
    LoginContract.View {
    private lateinit var loginAdapter: LoginAdapter
    override val viewModelInstance: LoginViewModel by viewModels()

    override fun initView() {
        supportActionBar?.hide()
        setupAdapter()
        observeViewModel()
    }

    override fun observeViewModel() {
        getViewModel().getLoginLiveData().observe(this, { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(true)
                    showError(false, null)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showError(false, null)
                    if (response.data?.first == Constant.ACTION_LOGIN) {
                        saveDataLogin(response.data.second)
                        navigateToHome()
                    } else {
                        getViewBinding().vpLogin.currentItem = 0
                        showSnackBarSuccess(getString(R.string.message_success_register))
                        loginAdapter.clearFieldFormRegister()
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    if (response.data?.first == Constant.ACTION_LOGIN) {
                        showError(true, getString(R.string.message_error_login))
                    } else {
                        showError(true, getString(R.string.message_error_register))
                    }
                }
            }
        })
    }

    override fun setupAdapter() {
        loginAdapter = LoginAdapter(
            listOf(
                getString(R.string.text_placeholder_sign_in),
                getString(R.string.text_placeholder_sign_up)
            )
        ) {
            hideSoftKeyboard()
            if (it.first == Constant.ITEM_TYPE_LOGIN) {
                loginUser(it.second)
            } else {
                registerUser(it.second)
            }
        }

        with(getViewBinding().vpLogin) {
            adapter = loginAdapter
            registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setButtonTextColor(position)
                }
            })
            getViewBinding().btnSignIn.setOnClickListener {
                currentItem -= 1
            }

            getViewBinding().btnSignUp.setOnClickListener {
                currentItem += 1
            }
        }
    }

    override fun navigateToHome() {
        MainActivity.startActivity(this)
    }

    override fun saveDataLogin(userEntity: UserEntity) {
        getViewModel().saveDataLogin(userEntity)
    }

    override fun loginUser(data: UserEntity) {
        val authRequest = AuthRequest(
            email = data.email,
            password = data.password
        )
        if (checkInternetConnection()) {
            getViewModel().loginUser(authRequest)
        } else {
            Utils.noInternetDialog(this,
                getString(R.string.text_placeholder_close),
                {
                    loginUser(data)
                    it?.dismiss()
                },
                {
                    it?.dismiss()
                }
            )
        }
    }

    override fun registerUser(data: UserEntity) {
        val authRequest = AuthRequest(
            username = data.username,
            email = data.email,
            password = data.password
        )
        if (checkInternetConnection()) {
            getViewModel().registerUser(authRequest)
        } else {
            Utils.noInternetDialog(this,
                getString(R.string.text_placeholder_close),
                {
                    registerUser(data)
                    it?.dismiss()
                },
                {
                    it?.dismiss()
                }
            )
        }
    }

    override fun showLoading(isLoading: Boolean) {
        getViewBinding().pbLoading.isVisible = isLoading
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        getViewBinding().tvErrorMessage.isVisible = isErrorEnabled
        if (msg.isNullOrEmpty().not()) getViewBinding().tvErrorMessage.text = msg
    }

    private fun setButtonTextColor(position: Int) {
        getViewBinding().tvErrorMessage.isVisible = false
        if (position == 0) {
            getViewBinding().btnSignIn.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.color_secondary
                )
            )
            getViewBinding().btnSignUp.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.color_secondary_variant
                )
            )
        } else {
            getViewBinding().btnSignIn.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.color_secondary_variant
                )
            )
            getViewBinding().btnSignUp.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.color_secondary
                )
            )
        }
    }

    companion object {
        @JvmStatic
        fun startActivity(context: Context?) {
            val intent = Intent(context, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context?.startActivity(intent)
        }
    }
}