package com.dev.divig.moviereviewsapp.ui.bottomsheetreview

import com.dev.divig.moviereviewsapp.data.local.room.datasource.MoviesDataSource
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity

class ReviewsBottomSheetRepository(
    private val localDataSource: MoviesDataSource
) : ReviewsBottomSheetContract.Repository {
    override suspend fun getReviewsByMovieId(movieId: Int): List<ReviewEntity> {
        return localDataSource.getReviewsByMovieId(movieId)
    }

    override suspend fun insertReview(review: ReviewEntity): Long {
        return localDataSource.insertReview(review)
    }

    override suspend fun deleteReview(review: ReviewEntity): Int {
        return localDataSource.deleteReview(review)
    }
}