package com.dev.divig.moviereviewsapp.ui.intro

import com.dev.divig.moviereviewsapp.data.local.datasource.LocalDataSource
import javax.inject.Inject

class IntroRepository @Inject constructor(private val localDataSource: LocalDataSource) : IntroContract.Repository {
    override fun setStateFirstRunApp(state: Boolean) {
        localDataSource.isFirstRunApp(state)
    }
}