package com.dev.divig.moviereviewsapp.ui.splashscreen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dev.divig.moviereviewsapp.ui.intro.IntroActivity
import com.dev.divig.moviereviewsapp.ui.login.LoginActivity
import com.dev.divig.moviereviewsapp.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashAppActivity : AppCompatActivity(),
    SplashAppContract.View {
    private val viewModel: SplashAppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkStateFirstRunApp()
        observeViewModel()
    }

    override fun observeViewModel() {
        viewModel.getStatusLiveData().observe(this) { response ->
            if (response) navigateToIntroPage() else navigateToMainPage()
        }
    }

    private fun checkStateFirstRunApp() {
        viewModel.checkStateFirstRunApp()
    }

    override fun navigateToIntroPage() {
        IntroActivity.startActivity(this)
    }

    override fun navigateToMainPage() {
        MainActivity.startActivity(this)
    }
}