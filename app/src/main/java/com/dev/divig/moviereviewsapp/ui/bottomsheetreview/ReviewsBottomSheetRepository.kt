package com.dev.divig.moviereviewsapp.ui.bottomsheetreview

import com.dev.divig.moviereviewsapp.data.local.room.datasource.MoviesDataSource
import com.dev.divig.moviereviewsapp.data.model.MovieEntity
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity

class ReviewsBottomSheetRepository(
    private val dataSource: MoviesDataSource
) : ReviewsBottomSheetContract.Repository {
    override suspend fun getReviewsByMovieId(): List<ReviewEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insertReview(review: ReviewEntity): Long {
        return dataSource.insertReview(review)
    }

    override suspend fun deleteReview(movie: MovieEntity): Int {
        return dataSource.deleteReview(movie)
    }

}