package com.dev.divig.moviereviewsapp.data.local.room.datasource

import com.dev.divig.moviereviewsapp.data.local.room.dao.MoviesDao
import com.dev.divig.moviereviewsapp.data.model.MovieEntity
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity

class MoviesDataSourceImpl(private val dao: MoviesDao) : MoviesDataSource {
    override suspend fun insertMovies(movies: List<MovieEntity>): Int {
        return dao.insertMovies(movies).size
    }

    override suspend fun getMovies(): List<MovieEntity> {
        return dao.getMovies()
    }

    override suspend fun getMovieById(id: Int): MovieEntity {
        return dao.getMovieById(id)
    }

    override suspend fun insertAllReview(reviews: List<ReviewEntity>): Int {
        return dao.insertAllReview(reviews).size
    }

    override suspend fun insertReview(review: ReviewEntity): Long {
        return dao.insertReview(review)
    }

    override suspend fun deleteReview(review: ReviewEntity): Int {
        return dao.deleteReview(review)
    }

    override suspend fun getReviewsByMovieId(movieId: Int): List<ReviewEntity> {
        return dao.getReviewsByMovieId(movieId)
    }
}