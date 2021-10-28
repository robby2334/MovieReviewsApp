package com.dev.divig.moviereviewsapp.ui.splashscreen

import com.dev.divig.moviereviewsapp.data.local.preference.MoviePreference
import com.dev.divig.moviereviewsapp.data.local.room.datasource.MoviesDataSource
import com.dev.divig.moviereviewsapp.data.model.MovieEntity

class SplashScreenRepository(
    private val localDataSource: MoviesDataSource,
    private val moviePreference: MoviePreference
) :
    SplashScreenContract.Repository {
    override suspend fun insertMovies(movies: List<MovieEntity>): Int {
        return localDataSource.insertMovies(movies)
    }

    override fun isFirstRunApp(): Boolean {
        return moviePreference.isFirstRunApp
    }
}