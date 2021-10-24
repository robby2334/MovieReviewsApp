package com.dev.divig.moviereviewsapp.ui.main

import com.dev.divig.moviereviewsapp.base.BaseContract
import com.dev.divig.moviereviewsapp.data.model.MovieEntity

interface MainActivityContract {
    interface View : BaseContract.BaseView {
        fun setupRecyclerView()
        fun setClickListeners(movie: MovieEntity)
    }

    interface Presenter : BaseContract.BasePresenter {

    }


}