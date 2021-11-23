package com.dev.divig.moviereviewsapp.ui.splashscreen

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
class SplashAppViewModel @Inject constructor(private val repository: SplashAppRepository) :
    ViewModel(),
    SplashAppContract.ViewModel {

    private val syncUserLiveData = MutableLiveData<Resource<UserEntity>>()

    override fun getSyncUserLiveData(): LiveData<Resource<UserEntity>> = syncUserLiveData

    override fun checkStateFirstRunApp(): Boolean {
        return repository.isFirstRunApp()
    }

    override fun checkLoginSession(): Boolean {
        return repository.isLoginSession()
    }

    override fun getSyncUser() {
        syncUserLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getSyncUser()
                val userEntity = UserEntity(
                    response.data.username,
                    response.data.email
                )
                viewModelScope.launch(Dispatchers.Main) {
                    syncUserLiveData.value = Resource.Success(userEntity)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    syncUserLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }
}