package com.dev.divig.moviereviewsapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.ui.detail.DetailActivity
import com.dev.divig.moviereviewsapp.utils.Constant
import com.dev.divig.moviereviewsapp.utils.DataDummy

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        findViewById<Button>(R.id.btn_click).setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(Constant.KEY_EXTRA_ID, DataDummy.getMovies()[0].id)
            startActivity(intent)
        }
    }
}