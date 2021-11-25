package com.dev.divig.moviereviewsapp.ui.main.profile.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomDialogViewModel @Inject constructor(private val repository: CustomDialogRepository) :
    ViewModel(), CustomDialogContract.ViewModel {
    private val repositoryLiveData = MutableLiveData<Resource<UserEntity>>()

    override fun getUserLiveData(): LiveData<Resource<UserEntity>> = repositoryLiveData

    override fun deleteLoginSession() {
        repository.deleteLoginSession()
    }

    override fun getUserData(): UserEntity {
        val mUsername = repository.loginUsername(null)
        val mEmail = repository.loginEmail(null)
        return UserEntity(mUsername, mEmail)
    }

    override fun updateUserData(username: String, email: String) {
        repositoryLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.putUserData(username, email)
                if (!response.isSuccess) {
                    viewModelScope.launch(Dispatchers.Main) {
                        repositoryLiveData.value = Resource.Error(response.errorMsg, UserEntity())
                    }
                } else {
                    val mUsername = response.data.username
                    val mEmail = response.data.email
                    repository.loginUsername(mUsername)
                    repository.loginEmail(mEmail)
                    val userEntity = UserEntity(mUsername, mEmail)
                    viewModelScope.launch(Dispatchers.Main) {
                        repositoryLiveData.value = Resource.Success(userEntity)
                    }
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    repositoryLiveData.value = Resource.Error(e.message.orEmpty(), UserEntity())
                }
            }
        }
    }
}