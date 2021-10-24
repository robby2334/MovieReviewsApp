package com.dev.divig.moviereviewsapp.ui.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev.divig.moviereviewsapp.R

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_page)
        supportActionBar?.hide()
    }
}