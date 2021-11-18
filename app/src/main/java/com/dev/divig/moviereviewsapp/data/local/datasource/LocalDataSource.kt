package com.dev.divig.moviereviewsapp.data.local.datasource

import com.dev.divig.moviereviewsapp.data.local.model.MovieEntity
import com.dev.divig.moviereviewsapp.data.local.model.ReviewEntity

interface LocalDataSource {
    fun isFirstRunApp(state: Boolean?): Boolean

    fun authToken(value: String?): String?

    fun isLoginSession(state: Boolean?): Boolean

    fun loginUsername(value: String?): String?

    fun loginEmail(value: String?): String?

    suspend fun insertMovies(movies: List<MovieEntity>): Int

    suspend fun insertMovie(movie: MovieEntity): Long

    suspend fun updateMovie(movie: MovieEntity): Int

    suspend fun setFavoriteMovie(movie: MovieEntity, newState: Boolean): Int

    suspend fun getMovies(): List<MovieEntity>

    suspend fun getMovieById(id: Int): MovieEntity

    suspend fun getFavMovies(): List<MovieEntity>

    suspend fun insertReviews(reviews: List<ReviewEntity>): List<Long>

    suspend fun getReviewsByMovieId(movieId: Int): List<ReviewEntity>
}