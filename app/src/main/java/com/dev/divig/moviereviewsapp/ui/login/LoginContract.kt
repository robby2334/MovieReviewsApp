package com.dev.divig.moviereviewsapp.ui.login

import androidx.lifecycle.LiveData
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.network.model.request.auth.AuthRequest
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.BaseAuthResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.UserData

interface LoginContract {
    interface View {
        fun initView()
        fun initViewModel()
    }

    interface ViewModel {
        fun getLoginResultLiveData(): LiveData<Resource<UserData>>
        fun saveSession(authToken: String)
        fun loginUser(loginRequest: AuthRequest)
    }

    interface Repository {
        fun saveSession(authToken: String)
        suspend fun postLoginUser(loginRequest: AuthRequest): BaseAuthResponse<UserData, String>
    }
}