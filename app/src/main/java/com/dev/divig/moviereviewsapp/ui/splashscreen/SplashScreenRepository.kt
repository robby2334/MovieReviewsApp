package com.dev.divig.moviereviewsapp.ui.splashscreen

import com.dev.divig.moviereviewsapp.data.local.preference.MoviePreference
import com.dev.divig.moviereviewsapp.data.local.room.datasource.MoviesDataSource
import com.dev.divig.moviereviewsapp.data.model.MovieEntity
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity

class SplashScreenRepository(
    private val localDataSource: MoviesDataSource,
    private val moviePreference: MoviePreference
) :
    SplashScreenContract.Repository {
    override suspend fun insertMovies(movies: List<MovieEntity>): Int {
        return localDataSource.insertMovies(movies)
    }

    override suspend fun insertAllReviews(reviews: List<ReviewEntity>): Int {
        return localDataSource.insertAllReview(reviews)
    }

    override suspend fun getMovies(): List<MovieEntity> {
        return localDataSource.getMovies()
    }

    override suspend fun getReviewsByMovieId(movieId: Int): List<ReviewEntity> {
        return localDataSource.getReviewsByMovieId(movieId)
    }

    override fun isFirstRunApp(): Boolean {
        return moviePreference.isFirstRunApp
    }
}