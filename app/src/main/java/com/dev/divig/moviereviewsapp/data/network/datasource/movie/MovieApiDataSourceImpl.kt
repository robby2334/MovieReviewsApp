package com.dev.divig.moviereviewsapp.data.network.datasource.movie

import com.dev.divig.moviereviewsapp.data.network.model.response.movie.BaseMovieResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.Movie
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.Review
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.detail.MovieDetailResponse
import com.dev.divig.moviereviewsapp.data.network.services.MovieApiService
import javax.inject.Inject

class MovieApiDataSourceImpl @Inject constructor(private val movieApiService: MovieApiService) :
    MovieApiDataSource {
    override suspend fun getMovies(): BaseMovieResponse<List<Movie>> {
        return movieApiService.getMovies()
    }

    override suspend fun getMovieDetail(id: String): MovieDetailResponse {
        return movieApiService.getMovieDetail(id)
    }

    override suspend fun searchMovies(query: String): BaseMovieResponse<List<Movie>> {
        return movieApiService.searchMovies(query)
    }

    override suspend fun getReviews(id: String): BaseMovieResponse<List<Review>> {
        return movieApiService.getReviews(id)
    }
}