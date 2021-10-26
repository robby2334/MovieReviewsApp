package com.dev.divig.moviereviewsapp.data.local.room.datasource

import com.dev.divig.moviereviewsapp.data.local.room.dao.MoviesDao
import com.dev.divig.moviereviewsapp.data.model.MovieEntity
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity

class MoviesDataSourceImpl(private val dao: MoviesDao) : MoviesDataSource {
    override suspend fun insertMovies(movies: List<MovieEntity>): Long {
        var result = 0L
        movies.forEach {
            result = dao.insertMovies(it)
        }
        return result
    }

    override suspend fun getMovies(): List<MovieEntity> {
        return dao.getMovies()
    }

    override suspend fun getMovieById(id: Int): MovieEntity {
        return dao.getMovieById(id)
    }

    override suspend fun insertReview(review: ReviewEntity): Long {
        return dao.insertReview(review)
    }

    override suspend fun deleteReview(movie: MovieEntity): Int {
        return dao.deleteReview(movie)
    }

    override suspend fun getReviewsByMovieId(movieId: Int): List<ReviewEntity> {
        return dao.getReviewsByMovieId(movieId)
    }
}