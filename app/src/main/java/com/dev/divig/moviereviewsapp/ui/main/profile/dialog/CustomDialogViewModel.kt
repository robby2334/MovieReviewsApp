package com.dev.divig.moviereviewsapp.ui.main.profile.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.UserEntity
import com.dev.divig.moviereviewsapp.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomDialogViewModel @Inject constructor(private val repository: CustomDialogRepository) :
    ViewModel(), CustomDialogContract.ViewModel {
    private val repositoryLiveData = MutableLiveData<Resource<Pair<Int, UserEntity>>>()

    override fun getUserLiveData(): LiveData<Resource<Pair<Int, UserEntity>>> = repositoryLiveData

    override fun setLoginSession() {
        repository.setLoginSession()
    }

    override fun getUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val mUsername = repository.loginUsername(null)
                val mEmail = repository.loginEmail(null)
                val userEntity = UserEntity(mUsername, mEmail)
                viewModelScope.launch(Dispatchers.Main) {
                    repositoryLiveData.value =
                        Resource.Success(Pair(Constant.ACTION_GET, userEntity))
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    repositoryLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }

    override fun updateUserData(username: String, email: String) {
        repositoryLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.putUserData(username, email)
                if (!response.isSuccess) {
                    viewModelScope.launch(Dispatchers.Main) {
                        repositoryLiveData.value =
                            Resource.Error(response.errorMsg)
                    }
                } else {
                    val mUsername = response.data.username
                    val mEmail = response.data.email
                    repository.loginUsername(mUsername)
                    repository.loginEmail(mEmail)
                    val userEntity = UserEntity(mUsername, mEmail)
                    viewModelScope.launch(Dispatchers.Main) {
                        repositoryLiveData.value =
                            Resource.Success(Pair(Constant.ACTION_UPDATE, userEntity))
                    }
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    repositoryLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }
}