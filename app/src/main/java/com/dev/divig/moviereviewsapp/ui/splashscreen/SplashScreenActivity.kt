package com.dev.divig.moviereviewsapp.ui.splashscreen

import android.content.Intent
import android.os.CountDownTimer
import com.dev.divig.moviereviewsapp.base.BaseActivity
import com.dev.divig.moviereviewsapp.data.local.preference.MoviePreference
import com.dev.divig.moviereviewsapp.databinding.ActivitySplashScreenBinding
import com.dev.divig.moviereviewsapp.ui.intro.IntroActivity
import com.dev.divig.moviereviewsapp.ui.main.MainActivity

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
        timer = object : CountDownTimer(3000, 1000) {
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
        val intent = Intent(this@SplashScreenActivity, IntroActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun navigateToMainPage() {
        val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.let {
            it.cancel()
            timer = null
        }
    }
}