package com.dev.divig.moviereviewsapp.ui.register

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
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository) :
    ViewModel(), RegisterContract.ViewModel {
    private val registerUserLiveData = MutableLiveData<Resource<UserData>>()

    override fun getRegisterResponseLiveData(): LiveData<Resource<UserData>> = registerUserLiveData

    override fun registerUser(registerRequest: AuthRequest) {
        registerUserLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.postRegister(registerRequest)
                viewModelScope.launch(Dispatchers.Main) {
                    registerUserLiveData.value = Resource.Success(response.data)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    registerUserLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }
}