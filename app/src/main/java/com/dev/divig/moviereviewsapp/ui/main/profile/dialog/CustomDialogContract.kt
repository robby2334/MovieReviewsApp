package com.dev.divig.moviereviewsapp.ui.main.profile.dialog

import androidx.lifecycle.LiveData
import com.dev.divig.moviereviewsapp.base.BaseContract
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.UserEntity
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.BaseAuthResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.UserData

interface CustomDialogContract {
    interface View : BaseContract.BaseView {
        fun setLoginSession()
        fun getUserData()
        fun updateUserData()
        fun fetchData(data: UserEntity)
    }

    interface ViewModel {
        fun getUserLiveData(): LiveData<Resource<UserEntity>>
        fun setLoginSession()
        fun getUserData(): UserEntity
        fun updateUserData(username: String, email: String)
    }

    interface Repository {
        fun setLoginSession()
        fun loginUsername(value: String?): String?
        fun loginEmail(value: String?): String?
        suspend fun putUserData(
            username: String,
            email: String
        ): BaseAuthResponse<UserData, String>
    }

}