package com.dev.divig.moviereviewsapp.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.ui.intropage.IntroPageActivity

class SplashScreenActivity : AppCompatActivity() {
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        setSplashScreenTimer()
        supportActionBar?.hide()
    }

    private fun setSplashScreenTimer() {
        timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                val intent = Intent(this@SplashScreenActivity, IntroPageActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

        }
        timer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.let {
            it.cancel()
            timer = null
        }
    }
}