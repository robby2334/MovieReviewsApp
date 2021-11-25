package com.dev.divig.moviereviewsapp.data.network.services

import com.dev.divig.moviereviewsapp.BuildConfig
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.BaseMovieResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.Movie
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.Review
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.detail.MovieDetailResponse
import com.dev.divig.moviereviewsapp.utils.Constant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MovieApiService {
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = Constant.API_KEY
    ): BaseMovieResponse<List<Movie>>

    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: String,
        @Query("api_key") apiKey: String = Constant.API_KEY,
        @Query("append_to_response") response: String = Constant.VIDEOS
    ): MovieDetailResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = Constant.API_KEY
    ): BaseMovieResponse<List<Movie>>

    @GET("movie/{id}/reviews")
    suspend fun getReviews(
        @Path("id") id: String,
        @Query("api_key") apiKey: String = Constant.API_KEY
    ): BaseMovieResponse<List<Review>>

    companion object {
        @JvmStatic
        operator fun invoke(): MovieApiService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.base_url_themoviedb)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(MovieApiService::class.java)
        }
    }
}