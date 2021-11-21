package com.dev.divig.moviereviewsapp.ui.main.profile.dialogchangeprofileandlogout

import androidx.lifecycle.LiveData
import com.dev.divig.moviereviewsapp.base.BaseContract
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.BaseAuthResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.UserData

interface ChangeProfileAndLogoutContract {
    interface View : BaseContract.BaseView{
        fun initView()
        fun checkFormValidation() : Boolean
        fun setLoginSession()
        fun updateUserData()
    }

    interface ViewModel{
        fun setLoginSession()
        fun updateUserData(username: String, email: String)
        fun getUserLiveData(): LiveData<Resource<UserData>>

    }

    interface Repository{
        fun setLoginSession()
        fun loginUsername(value : String?): String?
        fun loginEmail(value : String?): String?
        suspend fun putUserData(username: String, email: String): BaseAuthResponse<UserData, String>
    }

}