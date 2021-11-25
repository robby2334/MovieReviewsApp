package com.dev.divig.moviereviewsapp.ui.splashscreen

import androidx.lifecycle.LiveData
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.UserEntity
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.BaseAuthResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.UserData

interface SplashAppContract {
    interface View {
        fun observeViewModel()
        fun checkLoginSession()
        fun checkStateFirstRunApp()
        fun getSyncUser()
        fun navigateToIntroPage()
        fun navigateToMainPage()
        fun navigateToLoginPage()
    }

    interface ViewModel {
        fun getSyncUserLiveData(): LiveData<Resource<UserEntity>>
        fun checkStateFirstRunApp(): Boolean
        fun checkLoginSession(): Boolean
        fun getSyncUser()
    }

    interface Repository {
        fun isFirstRunApp(): Boolean
        fun isLoginSession(): Boolean
        suspend fun getSyncUser(): BaseAuthResponse<UserData, String>
    }
}