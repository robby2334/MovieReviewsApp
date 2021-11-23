package com.dev.divig.moviereviewsapp.ui.login

import com.dev.divig.moviereviewsapp.data.local.datasource.LocalDataSource
import com.dev.divig.moviereviewsapp.data.network.datasource.auth.AuthApiDataSource
import com.dev.divig.moviereviewsapp.data.network.model.request.auth.AuthRequest
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.BaseAuthResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.UserData
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val authApiDataSource: AuthApiDataSource,
    private val localDataSource: LocalDataSource
) : LoginContract.Repository {
    override fun saveLoginUsername(value: String?) {
        localDataSource.loginUsername(value)
    }

    override fun saveLoginEmail(value: String?) {
        localDataSource.loginEmail(value)
    }

    override fun saveSessionLogin(authToken: String) {
        localDataSource.authToken(authToken)
        localDataSource.isLoginSession(false)
    }

    override suspend fun postLoginUser(loginRequest: AuthRequest): BaseAuthResponse<UserData, String> {
        return authApiDataSource.postLoginUser(loginRequest)
    }

    override suspend fun postRegister(registerRequest: AuthRequest): BaseAuthResponse<UserData, String> {
        return authApiDataSource.postRegisterUser(registerRequest)
    }
}