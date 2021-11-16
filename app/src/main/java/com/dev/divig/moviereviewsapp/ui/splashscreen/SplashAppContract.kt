package com.dev.divig.moviereviewsapp.ui.splashscreen

import androidx.lifecycle.LiveData

interface SplashAppContract {
    interface View {
        fun observeViewModel()
        fun navigateToIntroPage()
        fun navigateToMainPage()
    }

    interface ViewModel {
        fun getStatusLiveData(): LiveData<Boolean>
        fun getMockDataLoading(): Boolean
        fun checkStateFirstRunApp()
    }

    interface Repository {
        fun isFirstRunApp(): Boolean
    }
}