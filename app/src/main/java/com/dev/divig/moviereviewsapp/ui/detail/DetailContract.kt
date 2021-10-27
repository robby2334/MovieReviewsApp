package com.dev.divig.moviereviewsapp.ui.detail

import com.dev.divig.moviereviewsapp.base.BaseContract
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.model.MovieEntity

interface DetailContract {
    interface View : BaseContract.BaseView {
        fun onDataCallback(response: Resource<MovieEntity>)
        fun getExtras()
        fun getMovieDetail(id: Int)
        fun fetchData(movie: MovieEntity)
    }

    interface Presenter : BaseContract.BasePresenter {
        fun getMovie(id: Int)
    }

    interface Repository {
        suspend fun getMovie(id: Int): MovieEntity
    }
}