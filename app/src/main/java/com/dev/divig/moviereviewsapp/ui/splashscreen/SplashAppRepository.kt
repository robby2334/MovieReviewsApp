package com.dev.divig.moviereviewsapp.ui.splashscreen

import com.dev.divig.moviereviewsapp.data.local.datasource.LocalDataSource
import javax.inject.Inject

class SplashAppRepository @Inject constructor(
    private val localDataSource: LocalDataSource
) :
    SplashAppContract.Repository {

    override fun isFirstRunApp(): Boolean {
        return localDataSource.isFirstRunApp(null)
    }
}