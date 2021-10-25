package com.dev.divig.moviereviewsapp.ui.splashscreen

import com.dev.divig.moviereviewsapp.data.local.preference.MoviePreference

class SplashScreenRepository(private val moviePreference: MoviePreference) :
    SplashScreenContract.Repository {
    override fun isFirstRunApp(): Boolean {
        return moviePreference.isFirstRunApp
    }
}