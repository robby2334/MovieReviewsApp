package com.dev.divig.moviereviewsapp.ui.detail

import com.dev.divig.moviereviewsapp.data.local.room.datasource.MoviesDataSource
import com.dev.divig.moviereviewsapp.data.model.MovieEntity
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity

class DetailRepository(private val dataSource: MoviesDataSource) : DetailContract.Repository {
    override suspend fun getMovie(id: Int): MovieEntity {
        return dataSource.getMovieById(id)
    }

    override suspend fun getReviewsByMovieId(movieId: Int): List<ReviewEntity> {
        return dataSource.getReviewsByMovieId(movieId)
    }
}