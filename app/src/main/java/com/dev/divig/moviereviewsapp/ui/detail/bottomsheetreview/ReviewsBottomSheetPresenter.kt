package com.dev.divig.moviereviewsapp.ui.detail.bottomsheetreview

import com.dev.divig.moviereviewsapp.base.BasePresenterImpl
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity
import com.dev.divig.moviereviewsapp.utils.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReviewsBottomSheetPresenter(
    private val view: ReviewsBottomSheetContract.View,
    private val repository: ReviewsBottomSheetContract.Repository
) :
    ReviewsBottomSheetContract.Presenter, BasePresenterImpl() {
    override fun getReviewsByMovieId(movieId: Int) {
        view.onLoading()
        scope.launch {
            try {
                delay(2000)
                val reviews = repository.getReviewsByMovieId(movieId)
                scope.launch(Dispatchers.Main) {
                    if (reviews.isNotEmpty()) {
                        view.onReadSuccess(reviews)
                    } else {
                        view.onDataEmpty()
                    }
                }
            } catch (ex: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onDataFailed(Pair(Constant.ZERO, ex.message.orEmpty()))
                }
            }
        }
    }

    override fun insertReview(review: ReviewEntity) {
        view.onLoading()
        scope.launch {
            try {
                val affectedRow = repository.insertReview(review)
                scope.launch(Dispatchers.Main) {
                    if (affectedRow > 0) {
                        view.onInsertSuccess()
                    } else {
                        view.onDataFailed(Pair(Constant.ACTION_INSERT, null))
                    }
                }
            } catch (ex: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onDataFailed(Pair(Constant.ZERO, ex.message.orEmpty()))
                }
            }
        }
    }

    override fun deleteReview(review: ReviewEntity) {
        view.onLoading()
        scope.launch {
            try {
                val affectedRow = repository.deleteReview(review)
                scope.launch(Dispatchers.Main) {
                    if (affectedRow > 0) {
                        view.onDeleteSuccess()
                    } else {
                        view.onDataFailed(Pair(Constant.ACTION_DELETE, null))
                    }
                }
            } catch (ex: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onDataFailed(Pair(Constant.ZERO, ex.message.orEmpty()))
                }
            }
        }
    }
}