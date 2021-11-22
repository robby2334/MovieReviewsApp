package com.dev.divig.moviereviewsapp.ui.main.favorite

import com.dev.divig.moviereviewsapp.data.local.datasource.LocalDataSource
import com.dev.divig.moviereviewsapp.data.local.model.MovieEntity
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val localDataSource: LocalDataSource) :
    FavoriteContract.Repository {
    override suspend fun getFavMovies(): List<MovieEntity> {
        return localDataSource.getFavMovies()
    }
}