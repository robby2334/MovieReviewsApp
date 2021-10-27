package com.dev.divig.moviereviewsapp.ui.splashscreen

import com.dev.divig.moviereviewsapp.data.local.preference.MoviePreference
import com.dev.divig.moviereviewsapp.data.local.room.datasource.MoviesDataSource
import com.dev.divig.moviereviewsapp.data.model.MovieEntity

class SplashScreenRepository(
    private val dataSource: MoviesDataSource,
    private val moviePreference: MoviePreference
) :
    SplashScreenContract.Repository {
    override suspend fun insertMovies(movies: List<MovieEntity>): Long {
        return dataSource.insertMovies(movies)
    }

    override fun getStateFirstRunApp(): Boolean {
        return moviePreference.isFirstRunApp
    }
}