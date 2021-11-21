package com.dev.divig.moviereviewsapp.ui.login

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseActivity
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.network.model.request.auth.AuthRequest
import com.dev.divig.moviereviewsapp.databinding.ActivityLoginBinding
import com.dev.divig.moviereviewsapp.ui.main.MainActivity
import com.dev.divig.moviereviewsapp.ui.register.RegisterActivity
import com.dev.divig.moviereviewsapp.utils.ValidationString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate),
    LoginContract.View{

    private val viewModel: LoginViewModel by viewModels()


    override fun toolBar() {
        supportActionBar?.title = getString(R.string.text_title_toolbar_login)
    }

    override fun onClickListener() {
        getViewBinding().btnLogin.setOnClickListener {
            if (checkValidation()) {
                viewModel.loginUser(
                    AuthRequest(
                        email = getViewBinding().etEmail.text.toString(),
                        password = getViewBinding().etPassword.text.toString()
                    )
                )
            }
        }
        getViewBinding().tvSingUp.setOnClickListener {
            navigateToRegister()
        }
    }

    override fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    override fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun setLoadingState(isLoadingVisible: Boolean) {
        getViewBinding().pbLoading.visibility = if (isLoadingVisible) View.VISIBLE else View.GONE
    }

    override fun checkValidation(): Boolean {
        val email = getViewBinding().etEmail.text.toString()
        val password = getViewBinding().etPassword.text.toString()
        var isFormValid = true
        //for checking is email empty
        when {
            email.isEmpty() -> {
                isFormValid = false
                getViewBinding().tilEmail.isErrorEnabled = true
                getViewBinding().tilEmail.error = getString(R.string.error_email_empty)
            }
            ValidationString.isEmailValid(email).not() -> {
                isFormValid = false
                getViewBinding().tilEmail.isErrorEnabled = true
                getViewBinding().tilEmail.error = getString(R.string.error_email_invalid)
            }
            else -> {
                getViewBinding().tilEmail.isErrorEnabled = false
            }
        }
        //for checking is Password empty
        if (password.isEmpty()) {
            isFormValid = false
            getViewBinding().tilPassword.isErrorEnabled = true
            getViewBinding().tilPassword.error = getString(R.string.error_password_empty)
        } else {
            getViewBinding().tilPassword.isErrorEnabled = false
        }
        return isFormValid
    }

    override fun initView() {
        initViewModel()
        toolBar()
        onClickListener()
    }

    override fun initViewModel() {
        viewModel.getLoginLiveData().observe(this, { response ->
            when (response) {
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    Toast.makeText(this, R.string.text_login_success, Toast.LENGTH_SHORT).show()
                    saveSessionLogin(response.data?.token)
                    navigateToHome()

                }
                is Resource.Error -> {
                    setLoadingState(false)
                    Toast.makeText(
                        this,
                        "Login Failed, Please check email and password correctly",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun saveSessionLogin(authToken: String?) {
        authToken?.let {
           viewModel.saveSessionLogin(authToken)
        }
    }
    companion object {
        @JvmStatic
        fun startActivity(context: Context?) {
            val intent = Intent(context, LoginActivity::class.java)
            context?.startActivity(intent)
        }
    }

}