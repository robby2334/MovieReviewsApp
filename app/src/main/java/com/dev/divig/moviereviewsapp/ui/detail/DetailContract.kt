package com.dev.divig.moviereviewsapp.ui.detail

import com.dev.divig.moviereviewsapp.base.BaseContract
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.model.MovieEntity
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity

interface DetailContract {
    interface View : BaseContract.BaseView {
        fun onDataCallback(response: Resource<MovieEntity>)
        fun onGetReviewSuccess(response: ReviewEntity)
        fun onDataReviewEmpty()
    }

    interface Presenter : BaseContract.BasePresenter {
        fun getMovie(id: Int)
        fun getReviewsByMovieId(movieId: Int)
    }

    interface Repository {
        suspend fun getMovie(id: Int): MovieEntity
        suspend fun getReviewsByMovieId(movieId: Int): List<ReviewEntity>
    }
}