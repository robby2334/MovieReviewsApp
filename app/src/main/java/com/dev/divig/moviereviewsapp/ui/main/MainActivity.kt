package com.dev.divig.moviereviewsapp.ui.main

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.data.local.room.MoviesDatabase
import com.dev.divig.moviereviewsapp.data.local.room.datasource.MoviesDataSourceImpl
import com.dev.divig.moviereviewsapp.ui.bottomsheetreview.ReviewsBottomSheet
import com.dev.divig.moviereviewsapp.utils.DataDummy
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getReview()

        findViewById<Button>(R.id.btn_show).setOnClickListener {
            val randomInt = (1..5).random()
            ReviewsBottomSheet(DataDummy.getMovies()[randomInt].id).show(
                supportFragmentManager,
                null
            )
        }
    }

    private fun insertAllReview() {
        GlobalScope.launch {
            MoviesDataSourceImpl(
                MoviesDatabase.getInstance(this@MainActivity).moviesDao()
            ).insertAllReview(DataDummy.getReviews())
        }
    }

    private fun getReview() {
        GlobalScope.launch {
            val result = MoviesDataSourceImpl(
                MoviesDatabase.getInstance(this@MainActivity).moviesDao()
            ).getReviewsByMovieId(580489)

            runOnUiThread {
                if (result.isEmpty()) {
                    insertAllReview()
                }
            }
        }
    }
}