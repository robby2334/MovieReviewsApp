package com.dev.divig.moviereviewsapp.ui.main.profile

import com.dev.divig.moviereviewsapp.data.local.datasource.LocalDataSource
import com.dev.divig.moviereviewsapp.data.network.datasource.auth.AuthApiDataSource
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.BaseAuthResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.UserData
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val authApiDataSource: AuthApiDataSource,
    private val localDataSource: LocalDataSource
) : ProfileContract.Repository {
    override suspend fun getUserData(): BaseAuthResponse<UserData, String> {
        return authApiDataSource.getUserData()
    }

    override fun loginUsername(value: String?): String? {
        return localDataSource.loginUsername(value)
    }

    override fun loginEmail(value: String?): String? {
        return localDataSource.loginEmail(value)
    }
}