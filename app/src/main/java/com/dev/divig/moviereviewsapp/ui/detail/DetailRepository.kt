package com.dev.divig.moviereviewsapp.ui.detail

import com.dev.divig.moviereviewsapp.data.local.datasource.LocalDataSource
import com.dev.divig.moviereviewsapp.data.local.model.MovieEntity
import com.dev.divig.moviereviewsapp.data.local.model.ReviewEntity
import com.dev.divig.moviereviewsapp.data.network.datasource.movie.MovieApiDataSource
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.BaseMovieResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.Review
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.detail.MovieDetailResponse
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val networkDataSource: MovieApiDataSource
) : DetailContract.Repository {
    override suspend fun updateMovie(movie: MovieEntity): Int {
        return localDataSource.updateMovie(movie)
    }

    override suspend fun setFavoriteMovie(movie: MovieEntity, newState: Boolean): Int {
        return localDataSource.setFavoriteMovie(movie, newState)
    }

    override suspend fun getDetailMovie(id: Int): MovieEntity {
        return localDataSource.getMovieById(id)
    }

    override suspend fun getDetailMovieFromNetwork(id: Int): MovieDetailResponse {
        return networkDataSource.getMovieDetail(id.toString())
    }

    override suspend fun insertReviews(reviews: List<ReviewEntity>): List<Long> {
        return localDataSource.insertReviews(reviews)
    }

    override suspend fun getReviews(movieId: Int): List<ReviewEntity> {
        return localDataSource.getReviewsByMovieId(movieId)
    }

    override suspend fun getReviewsFromNetwork(movieId: Int): BaseMovieResponse<List<Review>> {
        return networkDataSource.getReviews(movieId.toString())
    }
}