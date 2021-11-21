package com.dev.divig.moviereviewsapp.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.network.model.request.auth.AuthRequest
import com.dev.divig.moviereviewsapp.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: ProfileRepository) : ViewModel(),
    ProfileContract.ViewModel {
    private val repositoryLiveData = MutableLiveData<Resource<Pair<String,String>>>()
    override fun getUserLiveData(): LiveData<Resource<Pair<String,String>>> = repositoryLiveData


    override fun getUserData() {
        repositoryLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val username = repository.loginUsername(null).orEmpty()
                val email = repository.loginEmail(null).orEmpty()
                viewModelScope.launch(Dispatchers.Main) {
                    repositoryLiveData.value = Resource.Success(Pair(username,email))
                }
            }catch (e: Exception){
                viewModelScope.launch(Dispatchers.Main) {
                    repositoryLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }

}