package com.dev.divig.moviereviewsapp.ui.main.search

import com.dev.divig.moviereviewsapp.data.network.datasource.movie.MovieApiDataSource
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.BaseMovieResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.Movie
import javax.inject.Inject

class SearchRepository @Inject constructor(private val movieApiDataSource: MovieApiDataSource) :
    SearchContract.Repository {

    override suspend fun searchMovies(query: String): BaseMovieResponse<List<Movie>> {
        return movieApiDataSource.searchMovies(query)
    }
}