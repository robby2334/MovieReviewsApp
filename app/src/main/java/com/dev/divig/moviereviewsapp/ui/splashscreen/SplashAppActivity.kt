package com.dev.divig.moviereviewsapp.ui.splashscreen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.ui.intro.IntroActivity
import com.dev.divig.moviereviewsapp.ui.login.LoginActivity
import com.dev.divig.moviereviewsapp.ui.main.MainActivity
import com.dev.divig.moviereviewsapp.utils.Utils
import com.dev.divig.moviereviewsapp.utils.Utils.isInternetAvailable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashAppActivity : AppCompatActivity(),
    SplashAppContract.View {
    private val viewModel: SplashAppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        checkLoginSession()
        observeViewModel()
    }

    override fun observeViewModel() {
        viewModel.getSyncUserLiveData().observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    navigateToMainPage()
                }
                is Resource.Error -> {
                    Utils.showToastLong(this, getString(R.string.message_session_ended))
                    checkStateFirstRunApp()
                }
            }
        }
    }

    override fun checkLoginSession() {
        val response = viewModel.checkLoginSession()
        if (response) checkStateFirstRunApp() else getSyncUser()
    }

    override fun checkStateFirstRunApp() {
        val response = viewModel.checkStateFirstRunApp()
        if (response) navigateToIntroPage() else navigateToLoginPage()
    }

    override fun getSyncUser() {
        if (this.isInternetAvailable()) {
            viewModel.getSyncUser()
        } else {
            showNoInternetDialog()
        }
    }

    override fun navigateToIntroPage() {
        IntroActivity.startActivity(this)
    }

    override fun navigateToMainPage() {
        MainActivity.startActivity(this)
    }

    override fun navigateToLoginPage() {
        LoginActivity.startActivity(this)
    }

    private fun showNoInternetDialog() {
        Utils.noInternetDialog(
            this,
            getString(R.string.text_placeholder_exit),
            {
                it?.dismiss()
                checkLoginSession()
            },
            {
                it?.dismiss()
                finishAndRemoveTask()
            }
        )
    }
}