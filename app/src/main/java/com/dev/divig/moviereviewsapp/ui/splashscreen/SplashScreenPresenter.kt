package com.dev.divig.moviereviewsapp.ui.splashscreen

import com.dev.divig.moviereviewsapp.base.BasePresenterImpl

class SplashScreenPresenter(
    private val view: SplashScreenContract.View,
    private val repository: SplashScreenContract.Repository
) : SplashScreenContract.Presenter, BasePresenterImpl() {
    override fun checkStateFirstRunApp() {
        if (repository.getStateFirstRunApp()) {
            view.navigateToIntroPage()
        } else {
            view.navigateToMainPage()
        }
    }
}