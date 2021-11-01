package com.dev.divig.moviereviewsapp.ui.splashscreen

import com.dev.divig.moviereviewsapp.base.BaseContract
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.model.MovieEntity
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity

interface SplashScreenContract {
    interface View : BaseContract.BaseView {
        fun onDataCallback(response: Resource<Boolean>)
        fun insertData()
        fun setSplashScreenTimer()
        fun navigateToIntroPage()
        fun navigateToMainPage()
    }

    interface Presenter : BaseContract.BasePresenter {
        fun insertData(movies: List<MovieEntity>, reviews: List<ReviewEntity>)
        fun checkAvailabilityData(movieId: Int)
        fun checkStateFirstRunApp()
    }

    interface Repository {
        suspend fun insertMovies(movies: List<MovieEntity>): Int
        suspend fun insertAllReviews(reviews: List<ReviewEntity>): Int
        suspend fun getMovies(): List<MovieEntity>
        suspend fun getReviewsByMovieId(movieId: Int): List<ReviewEntity>
        fun isFirstRunApp(): Boolean
    }
}