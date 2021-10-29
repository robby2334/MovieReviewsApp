package com.dev.divig.moviereviewsapp.ui.bottomsheetreview

import com.dev.divig.moviereviewsapp.base.BasePresenterImpl

class ReviewsBottomSheetPresenter(
    private val view: ReviewsBottomSheetContract.View,
    private val repository: ReviewsBottomSheetContract.Repository
) :
    ReviewsBottomSheetContract.Presenter, BasePresenterImpl() {
    override fun getReviewsByMovieId() {
        TODO("Not yet implemented")
    }

    override fun insertReview() {
        TODO("Not yet implemented")
    }

    override fun deleteReview() {
        TODO("Not yet implemented")
    }

}