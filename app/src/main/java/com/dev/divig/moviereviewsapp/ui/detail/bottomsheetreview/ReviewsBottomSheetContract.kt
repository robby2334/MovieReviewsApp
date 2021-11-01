package com.dev.divig.moviereviewsapp.ui.detail.bottomsheetreview

import com.dev.divig.moviereviewsapp.base.BaseContract
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity

interface ReviewsBottomSheetContract {
    interface View : BaseContract.BaseView {
        fun onLoading()
        fun onReadSuccess(response: List<ReviewEntity>)
        fun onInsertSuccess()
        fun onDeleteSuccess()
        fun onDataFailed(response: Pair<Int, String?>)
        fun onDataEmpty()
    }

    interface Presenter : BaseContract.BasePresenter {
        fun getReviewsByMovieId(movieId: Int)
        fun insertReview(review: ReviewEntity)
        fun deleteReview(review: ReviewEntity)
    }

    interface Repository {
        suspend fun getReviewsByMovieId(movieId: Int): List<ReviewEntity>
        suspend fun insertReview(review: ReviewEntity): Long
        suspend fun deleteReview(review: ReviewEntity): Int
    }
}