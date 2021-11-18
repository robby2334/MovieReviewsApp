package com.dev.divig.moviereviewsapp.data.local.datasource

import com.dev.divig.moviereviewsapp.data.local.model.MovieEntity
import com.dev.divig.moviereviewsapp.data.local.model.ReviewEntity
import com.dev.divig.moviereviewsapp.data.local.preference.MoviePreference
import com.dev.divig.moviereviewsapp.data.local.room.dao.MoviesDao
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val moviePreference: MoviePreference,
    private val moviesDao: MoviesDao
) :
    LocalDataSource {
    override fun isFirstRunApp(state: Boolean?): Boolean {
        if (state != null) {
            moviePreference.isFirstRunApp = state
        }
        return moviePreference.isFirstRunApp
    }

    override fun authToken(value: String?): String? {
        if (!value.isNullOrEmpty()) {
            moviePreference.authToken = value
        }
        return moviePreference.authToken
    }

    override fun isLoginSession(state: Boolean?): Boolean {
        if (state != null) {
            moviePreference.isLoginSession = state
        }
        return moviePreference.isLoginSession
    }

    override fun loginUsername(value: String?): String? {
        if (!value.isNullOrEmpty()) {
            moviePreference.loginUsername = value
        }
        return moviePreference.loginUsername
    }

    override fun loginEmail(value: String?): String? {
        if (!value.isNullOrEmpty()) {
            moviePreference.loginEmail = value
        }
        return moviePreference.loginEmail
    }

    override suspend fun insertMovies(movies: List<MovieEntity>): Int {
        return moviesDao.insertMovies(movies).size
    }

    override suspend fun insertMovie(movie: MovieEntity): Long {
        return moviesDao.insertMovie(movie)
    }

    override suspend fun updateMovie(movie: MovieEntity): Int {
        return moviesDao.updateMovie(movie)
    }

    override suspend fun setFavoriteMovie(movie: MovieEntity, newState: Boolean): Int {
        movie.isFavorite = newState
        return moviesDao.updateMovie(movie)
    }

    override suspend fun getMovies(): List<MovieEntity> {
        return moviesDao.getMovies()
    }

    override suspend fun getMovieById(id: Int): MovieEntity {
        return moviesDao.getMovieById(id)
    }

    override suspend fun getFavMovies(): List<MovieEntity> {
        return moviesDao.getFavMovies()
    }

    override suspend fun insertReviews(reviews: List<ReviewEntity>): List<Long> {
        return moviesDao.insertReviews(reviews)
    }

    override suspend fun getReviewsByMovieId(movieId: Int): List<ReviewEntity> {
        return moviesDao.getReviewsByMovieId(movieId)
    }
}