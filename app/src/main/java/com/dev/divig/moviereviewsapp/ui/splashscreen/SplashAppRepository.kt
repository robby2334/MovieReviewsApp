package com.dev.divig.moviereviewsapp.ui.splashscreen

import com.dev.divig.moviereviewsapp.data.local.datasource.LocalDataSource
import com.dev.divig.moviereviewsapp.data.network.datasource.auth.AuthApiDataSource
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.BaseAuthResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.UserData
import javax.inject.Inject

class SplashAppRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val authApiDataSource: AuthApiDataSource
) :
    SplashAppContract.Repository {

    override fun isFirstRunApp(): Boolean {
        return localDataSource.isFirstRunApp(null)
    }

    override fun isLoginSession(): Boolean {
        return localDataSource.isLoginSession(null)
    }

    override suspend fun getSyncUser(): BaseAuthResponse<UserData, String> {
        return authApiDataSource.getSyncUser()
    }
}