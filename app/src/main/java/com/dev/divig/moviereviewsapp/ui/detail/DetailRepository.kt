package com.dev.divig.moviereviewsapp.ui.detail

import com.dev.divig.moviereviewsapp.data.local.room.datasource.MoviesDataSource
import com.dev.divig.moviereviewsapp.data.model.MovieEntity

class DetailRepository(private val dataSource: MoviesDataSource) : DetailContract.Repository {
    override suspend fun getMovie(id: Int): MovieEntity {
        return dataSource.getMovieById(id)
    }
}