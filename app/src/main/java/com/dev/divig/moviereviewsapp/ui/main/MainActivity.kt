package com.dev.divig.moviereviewsapp.ui.main

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.data.local.room.MoviesDatabase
import com.dev.divig.moviereviewsapp.data.local.room.datasource.MoviesDataSourceImpl
import com.dev.divig.moviereviewsapp.ui.detail.DetailActivity
import com.dev.divig.moviereviewsapp.utils.DataDummy
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        getMovies()

        findViewById<Button>(R.id.btn_click).setOnClickListener {
            DetailActivity.startActivity(this, DataDummy.getMovies()[(1..5).random()].id)
        }
    }

    private fun insertMovies() {
        GlobalScope.launch {
            MoviesDataSourceImpl(
                MoviesDatabase.getInstance(this@MainActivity).moviesDao()
            ).insertMovies(DataDummy.getMovies())
        }
    }

    private fun getMovies() {
        GlobalScope.launch {
            val result = MoviesDataSourceImpl(
                MoviesDatabase.getInstance(this@MainActivity).moviesDao()
            ).getMovies()

            runOnUiThread {
                if (result.isEmpty()) {
                    insertMovies()
                }
            }
        }
    }
}