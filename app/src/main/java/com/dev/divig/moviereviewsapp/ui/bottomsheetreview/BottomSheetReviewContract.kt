package com.dev.divig.moviereviewsapp.ui.bottomsheetreview

import com.dev.divig.moviereviewsapp.base.BaseContract
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.model.MovieEntity
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity

interface BottomSheetReviewContract {
    interface View : BaseContract.BaseView {
        fun onInsertSuccess(rowsAffected : Number)
        fun onDeleteSuccess(rowsAffected : Number)
        fun onUpdateSuccess(rowsAffected : Number)
        fun onDataFailed(msg : String?)
        fun setListData(data: List<ReviewEntity>)
    }

    interface Presenter : BaseContract.BasePresenter {
        fun getReviewsByMovieId()
        fun insertReview()
        fun deleteReview()
    }

    interface Repository {
        suspend fun getReviewsByMovieId(): List<ReviewEntity>
        suspend fun insertReview(review: ReviewEntity): Long
        suspend fun deleteReview(movie: MovieEntity): Int
    }
}