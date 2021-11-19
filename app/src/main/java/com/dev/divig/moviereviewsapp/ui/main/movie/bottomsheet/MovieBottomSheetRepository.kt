package com.dev.divig.moviereviewsapp.ui.main.movie.bottomsheet

import com.dev.divig.moviereviewsapp.data.local.datasource.LocalDataSource
import com.dev.divig.moviereviewsapp.data.local.model.MovieEntity
import javax.inject.Inject

class MovieBottomSheetRepository @Inject constructor(private val localDataSource: LocalDataSource) :
    MovieBottomSheetContract.Repository {

    override suspend fun getMovies(): List<MovieEntity> {
        return localDataSource.getMovies()
    }
}