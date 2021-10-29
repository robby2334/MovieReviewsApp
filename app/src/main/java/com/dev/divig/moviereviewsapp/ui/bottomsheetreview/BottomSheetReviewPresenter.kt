package com.dev.divig.moviereviewsapp.ui.bottomsheetreview

import com.dev.divig.moviereviewsapp.base.BasePresenterImpl

class BottomSheetReviewPresenter(
    private val view: BottomSheetReviewContract.View,
    private val repository: BottomSheetReviewContract.Repository
) :
    BottomSheetReviewContract.Presenter, BasePresenterImpl() {
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