package com.dev.divig.moviereviewsapp.ui.detail.bottomsheetreview

import com.dev.divig.moviereviewsapp.data.local.datasource.LocalDataSource
import com.dev.divig.moviereviewsapp.data.local.model.ReviewEntity
import javax.inject.Inject

class ReviewsBottomSheetRepository @Inject constructor(
    private val localDataSource: LocalDataSource
) : ReviewsBottomSheetContract.Repository {
    override suspend fun getReviewsByMovieId(movieId: Int): List<ReviewEntity> {
        return localDataSource.getReviewsByMovieId(movieId)
    }
}