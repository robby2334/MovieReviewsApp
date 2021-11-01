package com.dev.divig.moviereviewsapp.data.local.room.datasource

import com.dev.divig.moviereviewsapp.data.model.MovieEntity
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity

interface MoviesDataSource {
    suspend fun insertMovies(movies: List<MovieEntity>): Int

    suspend fun getMovies(): List<MovieEntity>

    suspend fun getMovieById(id: Int): MovieEntity

    suspend fun insertAllReview(reviews: List<ReviewEntity>): Int

    suspend fun insertReview(review: ReviewEntity): Long

    suspend fun deleteReview(review: ReviewEntity): Int

    suspend fun getReviewsByMovieId(movieId: Int): List<ReviewEntity>
}