package com.dev.divig.moviereviewsapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.UserEntity
import com.dev.divig.moviereviewsapp.data.network.model.request.auth.AuthRequest
import com.dev.divig.moviereviewsapp.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) :
    ViewModel(), LoginContract.ViewModel {

    private val syncUserLiveData = MutableLiveData<Resource<Pair<Int, UserEntity>>>()

    override fun getLoginLiveData(): LiveData<Resource<Pair<Int, UserEntity>>> = syncUserLiveData

    override fun saveDataLogin(userEntity: UserEntity) {
        repository.saveLoginUsername(userEntity.username.orEmpty())
        repository.saveLoginEmail(userEntity.email.orEmpty())
        repository.saveSessionLogin(userEntity.token.orEmpty())
    }

    override fun loginUser(loginRequest: AuthRequest) {
        syncUserLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.postLoginUser(loginRequest)
                if (!response.isSuccess) {
                    viewModelScope.launch(Dispatchers.Main) {
                        syncUserLiveData.value = Resource.Error(
                            response.errorMsg,
                            Pair(Constant.ACTION_LOGIN, UserEntity())
                        )
                    }
                } else {
                    val userEntity = UserEntity(
                        username = response.data.username,
                        email = response.data.email,
                        token = response.data.token
                    )
                    viewModelScope.launch(Dispatchers.Main) {
                        syncUserLiveData.value =
                            Resource.Success(Pair(Constant.ACTION_LOGIN, userEntity))
                    }
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    syncUserLiveData.value = Resource.Error(
                        e.message.orEmpty(),
                        Pair(Constant.ACTION_LOGIN, UserEntity())
                    )
                }
            }
        }
    }

    override fun registerUser(registerRequest: AuthRequest) {
        syncUserLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.postRegister(registerRequest)
                if (!response.isSuccess) {
                    viewModelScope.launch(Dispatchers.Main) {
                        syncUserLiveData.value = Resource.Error(
                            response.errorMsg,
                            Pair(Constant.ACTION_REGISTER, UserEntity())
                        )
                    }
                } else {
                    viewModelScope.launch(Dispatchers.Main) {
                        syncUserLiveData.value =
                            Resource.Success(Pair(Constant.ACTION_REGISTER, UserEntity()))
                    }
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    syncUserLiveData.value = Resource.Error(
                        e.message.orEmpty(),
                        Pair(Constant.ACTION_REGISTER, UserEntity())
                    )
                }
            }
        }
    }

}