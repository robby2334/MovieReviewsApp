package com.dev.divig.moviereviewsapp.ui.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashAppViewModel @Inject constructor(private val repository: SplashAppRepository) :
    ViewModel(),
    SplashAppContract.ViewModel {

    private val statusRepositoryLiveData = MutableLiveData<Boolean>()

    override fun getStatusLiveData(): LiveData<Boolean> = statusRepositoryLiveData

    override fun checkStateFirstRunApp() {
        statusRepositoryLiveData.value = repository.isFirstRunApp()
    }
}