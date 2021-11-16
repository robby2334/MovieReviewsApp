package com.dev.divig.moviereviewsapp.ui.main.movie

import com.dev.divig.moviereviewsapp.data.local.datasource.LocalDataSource
import com.dev.divig.moviereviewsapp.data.local.model.MovieEntity
import com.dev.divig.moviereviewsapp.data.network.datasource.movie.MovieApiDataSource
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.BaseMovieResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.Movie
import javax.inject.Inject

class MovieFragmentRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val movieApiDataSource: MovieApiDataSource
) : MovieFragmentContract.Repository {
    override suspend fun insertMovies(movies: List<MovieEntity>): Int {
        return localDataSource.insertMovies(movies)
    }

    override suspend fun getMovies(): List<MovieEntity> {
        return localDataSource.getMovies()
    }

    override suspend fun getMoviesFromNetwork(): BaseMovieResponse<List<Movie>> {
        return movieApiDataSource.getMovies()
    }
}