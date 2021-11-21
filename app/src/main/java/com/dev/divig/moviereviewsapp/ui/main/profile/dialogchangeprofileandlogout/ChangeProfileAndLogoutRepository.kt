package com.dev.divig.moviereviewsapp.ui.main.profile.dialogchangeprofileandlogout

import com.dev.divig.moviereviewsapp.data.local.datasource.LocalDataSource
import com.dev.divig.moviereviewsapp.data.network.datasource.auth.AuthApiDataSource
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.BaseAuthResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.UserData
import javax.inject.Inject

class ChangeProfileAndLogoutRepository @Inject constructor(
    private val authApiDataSource: AuthApiDataSource,
    private val localDataSource: LocalDataSource
) : ChangeProfileAndLogoutContract.Repository {
    override fun setLoginSession() {
        localDataSource.isLoginSession(false)
    }

    override fun loginUsername(value: String?): String? {
        return localDataSource.loginUsername(value)
    }

    override fun loginEmail(value: String?): String? {
        return localDataSource.loginEmail(value)
    }

    override suspend fun putUserData(username: String, email: String): BaseAuthResponse<UserData, String> {
        return authApiDataSource.putUserData(username,email)
    }
}