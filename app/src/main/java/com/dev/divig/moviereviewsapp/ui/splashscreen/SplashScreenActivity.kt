package com.dev.divig.moviereviewsapp.ui.splashscreen

import android.os.CountDownTimer
import com.dev.divig.moviereviewsapp.base.BaseActivity
import com.dev.divig.moviereviewsapp.data.local.preference.MoviePreference
import com.dev.divig.moviereviewsapp.databinding.ActivitySplashScreenBinding
import com.dev.divig.moviereviewsapp.utils.Constant

class SplashScreenActivity :
    BaseActivity<ActivitySplashScreenBinding, SplashScreenContract.Presenter>(
        ActivitySplashScreenBinding::inflate
    ),
    SplashScreenContract.View {
    private var timer: CountDownTimer? = null

    override fun initView() {
        setSplashScreenTimer()
        supportActionBar?.hide()
    }

    override fun initPresenter() {
        val repository = SplashScreenRepository(MoviePreference(this))
        setPresenter(SplashScreenPresenter(this, repository))
    }

    override fun setSplashScreenTimer() {
        timer = object : CountDownTimer(Constant.THREE_SECOND, Constant.ONE_SECOND) {
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                checkStateFirstRunApp()
            }
        }
        timer?.start()
    }

    override fun checkStateFirstRunApp() {
        getPresenter().checkStateFirstRunApp()
    }

    override fun navigateToIntroPage() {
        // TODO: 27-Oct-21 Navigate to IntroPage
    }

    override fun navigateToMainPage() {
        // TODO: 27-Oct-21 Navigate to MainPage
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.let {
            it.cancel()
            timer = null
        }
    }
}