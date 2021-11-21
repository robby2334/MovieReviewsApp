package com.dev.divig.moviereviewsapp.ui.register

import com.dev.divig.moviereviewsapp.data.network.datasource.auth.AuthApiDataSource
import com.dev.divig.moviereviewsapp.data.network.model.request.auth.AuthRequest
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.BaseAuthResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.UserData
import javax.inject.Inject

class RegisterRepository
@Inject constructor(
    private val authApiDataSource: AuthApiDataSource
) : RegisterContract.Repository {
    override suspend fun postRegister(registerRequest: AuthRequest): BaseAuthResponse<UserData, String> {
        return authApiDataSource.postRegisterUser(registerRequest)
    }
}