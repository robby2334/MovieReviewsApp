package com.dev.divig.moviereviewsapp.ui.bottomsheetreview

import com.dev.divig.moviereviewsapp.base.BaseContract
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity

interface BottomSheetActivityContract {
    interface View : BaseContract.BaseView {
        fun getData()
        fun onDataCallback(response: Resource<List<ReviewEntity>>)
        fun initList()
        fun setListData(data: List<ReviewEntity>)
    }

    interface Presenter : BaseContract.BasePresenter {
        fun getAllReviews()
    }

    interface Repository {
        suspend fun getAllReviews(): List<ReviewEntity>
    }
}