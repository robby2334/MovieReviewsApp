package com.dev.divig.moviereviewsapp.data.network.datasource.auth

import com.dev.divig.moviereviewsapp.data.network.model.request.auth.AuthRequest
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.BaseAuthResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.UserData
import com.dev.divig.moviereviewsapp.data.network.services.AuthApiService
import okhttp3.MultipartBody
import javax.inject.Inject

class AuthApiDataSourceImpl
@Inject constructor(private val authApiService: AuthApiService) : AuthApiDataSource {
    override suspend fun postLoginUser(loginRequest: AuthRequest): BaseAuthResponse<UserData, String> {
        return authApiService.postLoginUser(loginRequest)
    }

    override suspend fun postRegisterUser(registerRequest: AuthRequest): BaseAuthResponse<UserData, String> {
        return authApiService.postRegisterUser(registerRequest)
    }

    override suspend fun getSyncUser(): BaseAuthResponse<UserData, String> {
        return authApiService.getSyncUser()
    }

    override suspend fun getUserData(): BaseAuthResponse<UserData, String> {
        return authApiService.getUserData()
    }

    override suspend fun putUserData(
        username: String,
        email: String
    ): BaseAuthResponse<UserData, String> {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("email", email)
            .addFormDataPart("username", username)
            .build()
        return authApiService.putUserData(requestBody)
    }

}