package com.dev.divig.moviereviewsapp.ui.main.profile

import androidx.lifecycle.LiveData
import com.dev.divig.moviereviewsapp.base.BaseContract
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.BaseAuthResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.UserData

interface ProfileContract {
    interface View : BaseContract.BaseView{
        fun getUserData()
        fun fetchData(username : String, email: String)
        fun setClickListeners()
        fun showChangeProfileDialog()
        fun showAboutDialog()
        fun showLogoutDialog()
    }

    interface ViewModel{
        fun getUserLiveData(): LiveData<Resource<Pair<String,String>>>
        fun getUserData()
    }

    interface Repository{
        suspend fun getUserData(): BaseAuthResponse<UserData, String>
        fun loginUsername(value : String?): String?
        fun loginEmail(value : String?): String?
    }


}