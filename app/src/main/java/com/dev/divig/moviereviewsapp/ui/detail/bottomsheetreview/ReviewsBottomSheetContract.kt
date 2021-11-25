package com.dev.divig.moviereviewsapp.ui.detail.bottomsheetreview

import androidx.lifecycle.LiveData
import com.dev.divig.moviereviewsapp.base.BaseContract
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.ReviewEntity

interface ReviewsBottomSheetContract {
    interface View : BaseContract.BaseView {
        fun getReviews()
        fun setListData(reviews: List<ReviewEntity>?)
    }

    interface ViewModel {
        fun getReviewsLiveData() : LiveData<Resource<List<ReviewEntity>>>
        fun getReviewsByMovieId(movieId: Int)
    }

    interface Repository {
        suspend fun getReviewsByMovieId(movieId: Int): List<ReviewEntity>
    }
}