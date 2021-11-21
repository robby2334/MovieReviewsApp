package com.dev.divig.moviereviewsapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.network.model.request.auth.AuthRequest
import com.dev.divig.moviereviewsapp.data.network.model.response.auth.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) :
    ViewModel(), LoginContract.ViewModel {

    private val syncUserLiveData = MutableLiveData<Resource<UserData>>()

    override fun getLoginLiveData(): LiveData<Resource<UserData>> = syncUserLiveData

    override fun saveSessionLogin(authToken: String) {
        repository.saveSessionLogin(authToken)
    }

    override fun loginUser(loginRequest: AuthRequest) {
        syncUserLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.postLoginUser(loginRequest)
                viewModelScope.launch(Dispatchers.Main) {
                    syncUserLiveData.value = Resource.Success(response.data)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    syncUserLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }

}