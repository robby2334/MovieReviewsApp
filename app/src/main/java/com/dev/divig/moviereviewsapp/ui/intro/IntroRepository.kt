package com.dev.divig.moviereviewsapp.ui.intro

import com.dev.divig.moviereviewsapp.data.local.preference.MoviePreference

class IntroRepository(private val preference: MoviePreference) : IntroContract.Repository {
    override fun setStateFirstRunApp(state: Boolean) {
        preference.isFirstRunApp = state
    }
}