package com.dev.divig.moviereviewsapp.ui.register


import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseActivity
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.network.model.request.auth.AuthRequest
import com.dev.divig.moviereviewsapp.databinding.ActivityRegisterBinding
import com.dev.divig.moviereviewsapp.ui.login.LoginActivity
import com.dev.divig.moviereviewsapp.utils.ValidationString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>(
    ActivityRegisterBinding::inflate
), RegisterContract.View {
    private val viewModel: RegisterViewModel by viewModels()



    override fun toolbar() {
        supportActionBar?.title = getString(R.string.text_title_toolbar_register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun loadingState(isLoadingVisible: Boolean) {
        getViewBinding().pbLoading.visibility = if (isLoadingVisible) View.VISIBLE else View.GONE
    }

    override fun checkValidation(): Boolean {
        val email = getViewBinding().etEmail.text.toString()
        val username = getViewBinding().etUsername.text.toString()
        val password = getViewBinding().etPassword.text.toString()
        var isFormValid = true

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
        if (password.isEmpty()) {
            isFormValid = false
            getViewBinding().tilPassword.isErrorEnabled = true
            getViewBinding().tilPassword.error = getString(R.string.error_password_empty)
        } else {
            getViewBinding().tilPassword.isErrorEnabled = false
        }
        if (username.isEmpty()) {
            isFormValid = false
            getViewBinding().tilUsername.isErrorEnabled = true
            getViewBinding().tilUsername.error = getString(R.string.error_username_empty)
        } else {
            getViewBinding().tilUsername.isErrorEnabled = false
        }
        return isFormValid
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    override fun initView() {
        initViewModel()
        toolbar()
        setOnClickListener()
    }

    override fun initViewModel() {
        viewModel.getRegisterResponseLiveData().observe(this, { response ->
            when (response) {
                is Resource.Loading -> {
                    loadingState(true)
                }
                is Resource.Success -> {
                    loadingState(false)
                    Toast.makeText(this, R.string.text_register_success, Toast.LENGTH_SHORT).show()
                    navigateToLogin()
                }
                is Resource.Error -> {
                    loadingState(false)
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()

                }
            }
        })
    }

    override fun setOnClickListener() {
        getViewBinding().btnRegister.setOnClickListener {
            if(checkValidation()){
                viewModel.registerUser(
                    AuthRequest(
                        email = getViewBinding().etEmail.text.toString(),
                        password = getViewBinding().etPassword.text.toString(),
                        username = getViewBinding().etUsername.text.toString()
                    )
                )
            }
        }
    }


}