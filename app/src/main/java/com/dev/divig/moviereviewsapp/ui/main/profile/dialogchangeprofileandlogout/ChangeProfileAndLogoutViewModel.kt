package com.dev.divig.moviereviewsapp.ui.main.profile.dialogchangeprofileandlogout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeProfileAndLogoutViewModel @Inject constructor(private val repository: ChangeProfileAndLogoutRepository) :
    ViewModel(),ChangeProfileAndLogoutContract.ViewModel {
    private val repositoryLiveData = MutableLiveData<Resource<UserData>>()
    override fun setLoginSession() {
        repository.setLoginSession()
    }

    override fun updateUserData(username: String, email: String) {
        repositoryLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.putUserData(username,email)
                if (!response.isSuccess) {
                    viewModelScope.launch(Dispatchers.Main) {
                        repositoryLiveData.value =
                            Resource.Error(response.errorMsg)
                    }
                } else {

                    repository.loginUsername(response.data.username)
                    repository.loginEmail(response.data.email)
                    viewModelScope.launch(Dispatchers.Main) {
                        repositoryLiveData.value = Resource.Success(response.data)
                    }
                }

            }catch (e: Exception){
                viewModelScope.launch(Dispatchers.Main) {
                    repositoryLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }

    override fun getUserLiveData(): LiveData<Resource<UserData>> = repositoryLiveData
}