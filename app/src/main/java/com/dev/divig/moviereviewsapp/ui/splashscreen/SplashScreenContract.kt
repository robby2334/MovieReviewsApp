package com.dev.divig.moviereviewsapp.ui.splashscreen

import com.dev.divig.moviereviewsapp.base.BaseContract
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.model.MovieEntity

interface SplashScreenContract {
    interface View : BaseContract.BaseView {
        fun onDataCallback(response: Resource<Boolean>)
        fun insertMovies()
        fun setSplashScreenTimer()
        fun checkStateFirstRunApp()
        fun navigateToIntroPage()
        fun navigateToMainPage()
    }

    interface Presenter : BaseContract.BasePresenter {
        fun insertMovies(movies: List<MovieEntity>)
        fun checkStateFirstRunApp()
    }

    interface Repository {
        suspend fun insertMovies(movies: List<MovieEntity>): Long
        fun getStateFirstRunApp(): Boolean
    }
}