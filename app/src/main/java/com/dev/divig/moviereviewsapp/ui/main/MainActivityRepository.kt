package com.dev.divig.moviereviewsapp.ui.main

import com.dev.divig.moviereviewsapp.data.local.room.datasource.MoviesDataSourceImpl
import com.dev.divig.moviereviewsapp.data.model.MovieEntity

class MainActivityRepository(
    private val dataSourceImpl: MoviesDataSourceImpl
) : MainActivityContract.Repository {

    override suspend fun getMovies(): List<MovieEntity> {
        return dataSourceImpl.getMovies()
    }
}