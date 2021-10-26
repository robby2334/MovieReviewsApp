package com.dev.divig.moviereviewsapp.ui.intro

import com.dev.divig.moviereviewsapp.base.BaseContract

interface IntroContract {
    interface View : BaseContract.BaseView {
        fun setIntro()
        fun setupIndicator()
        fun setCurrentIndicator(position: Int)
        fun navigateToMainPage()
    }

    interface Presenter : BaseContract.BasePresenter {
        fun setStateFirstRunApp()
    }

    interface Repository {
        fun setStateFirstRunApp(state: Boolean)
    }
}