package com.dev.divig.moviereviewsapp.base

interface BaseContract {

    interface BaseView {
        fun observeViewModel()
        fun showContent(isContentVisible: Boolean)
        fun showLoading(isLoading: Boolean)
        fun showError(isErrorEnabled: Boolean, msg: String?)
    }
}