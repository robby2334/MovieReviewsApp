package com.dev.divig.moviereviewsapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.divig.moviereviewsapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }
}