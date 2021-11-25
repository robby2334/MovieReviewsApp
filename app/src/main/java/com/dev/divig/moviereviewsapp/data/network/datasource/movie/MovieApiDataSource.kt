package com.dev.divig.moviereviewsapp.data.network.datasource.movie

import com.dev.divig.moviereviewsapp.data.network.model.response.movie.BaseMovieResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.Movie
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.Review
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.detail.MovieDetailResponse

interface MovieApiDataSource {
    suspend fun getMovies(): BaseMovieResponse<List<Movie>>
    suspend fun getMovieDetail(id: String): MovieDetailResponse
    suspend fun searchMovies(query: String): BaseMovieResponse<List<Movie>>
    suspend fun getReviews(id: String): BaseMovieResponse<List<Review>>
}