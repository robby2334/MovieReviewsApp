package com.dev.divig.moviereviewsapp.ui.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashAppViewModel @Inject constructor(private val repository: SplashAppRepository) :
    ViewModel(),
    SplashAppContract.ViewModel {

    private val statusRepositoryLiveData = MutableLiveData<Boolean>()

    private var dataLoaded: Boolean = false

    override fun getStatusLiveData(): LiveData<Boolean> = statusRepositoryLiveData

    override fun getMockDataLoading(): Boolean {
        viewModelScope.launch {
            delay(3000)
            dataLoaded = true
        }
        return dataLoaded
    }

    override fun checkStateFirstRunApp() {
        statusRepositoryLiveData.value = repository.isFirstRunApp()
    }
}