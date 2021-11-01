package com.dev.divig.moviereviewsapp.ui.intro

import com.dev.divig.moviereviewsapp.base.BasePresenterImpl

class IntroPresenter(
    private val repository: IntroContract.Repository
) : IntroContract.Presenter, BasePresenterImpl() {

    override fun setStateFirstRunApp() {
        repository.setStateFirstRunApp(false)
    }
}
