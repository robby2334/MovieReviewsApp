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
        fun toolBar()
        fun onClickListener()
        fun navigateToHome()
        fun navigateToRegister()
        fun setLoadingState(isLoadingVisible: Boolean)
        fun checkValidation() : Boolean
        fun saveSessionLogin(authToken : String?)
    }

    interface ViewModel {
        fun getLoginLiveData(): LiveData<Resource<UserData>>
        fun saveSessionLogin(authToken: String)
        fun loginUser(loginRequest: AuthRequest)
    }

    interface Repository {
        fun saveSessionLogin(authToken: String)
        suspend fun postLoginUser(loginRequest: AuthRequest): BaseAuthResponse<UserData, String>
    }
}