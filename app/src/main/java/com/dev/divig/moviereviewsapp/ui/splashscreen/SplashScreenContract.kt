package com.dev.divig.moviereviewsapp.ui.splashscreen

import com.dev.divig.moviereviewsapp.base.BaseContract

interface SplashScreenContract {
    interface View : BaseContract.BaseView{
        fun setSplashScreenTimer()
        fun checkStateFirstRunApp()
        fun navigateToIntroPage()
        fun navigateToMainPage()
    }

    interface Presenter : BaseContract.BasePresenter {
        fun checkStateFirstRunApp()
    }

    interface Repository {
        fun isFirstRunApp() : Boolean
    }
}