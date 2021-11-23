package com.dev.divig.moviereviewsapp.ui.login

import androidx.lifecycle.LiveData
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.UserEntity
import com.dev.divig.moviereviewsapp.data.network.model.request.auth.AuthRequest
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.BaseAuthResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.UserData

interface LoginContract {
    interface View {
        fun setupAdapter()
        fun navigateToHome()
        fun saveDataLogin(userEntity: UserEntity)
        fun loginUser(data: UserEntity)
        fun registerUser(data: UserEntity)
    }

    interface ViewModel {
        fun getLoginLiveData(): LiveData<Resource<Pair<Int, UserEntity>>>
        fun saveDataLogin(userEntity: UserEntity)
        fun loginUser(loginRequest: AuthRequest)
        fun registerUser(registerRequest: AuthRequest)
    }

    interface Repository {
        fun saveLoginUsername(value : String?)
        fun saveLoginEmail(value : String?)
        fun saveSessionLogin(authToken: String)
        suspend fun postLoginUser(loginRequest: AuthRequest): BaseAuthResponse<UserData, String>
        suspend fun postRegister(registerRequest: AuthRequest): BaseAuthResponse<UserData, String>
    }
}