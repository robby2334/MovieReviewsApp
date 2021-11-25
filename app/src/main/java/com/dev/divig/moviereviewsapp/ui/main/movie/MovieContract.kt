package com.dev.divig.moviereviewsapp.ui.main.movie

import androidx.lifecycle.LiveData
import com.dev.divig.moviereviewsapp.base.BaseContract
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.MovieEntity
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.BaseMovieResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.Movie

interface MovieContract {
    interface View : BaseContract.BaseView {
        fun getMovies(update: Boolean)
        fun setupRecyclerView(movies: List<MovieEntity>)
        fun getMovieFilters(
            movies: List<MovieEntity>,
            type: Int,
            genre: String? = null
        ): List<MovieEntity>
        fun initSwipeRefresh()
    }

    interface ViewModel {
        fun getMoviesLiveData(): LiveData<Resource<List<MovieEntity>>>
        fun getMovies(update: Boolean)
        fun getMovieFilters(
            movies: List<MovieEntity>,
            type: Int,
            genre: String?
        ): List<MovieEntity>
    }

    interface Repository {
        suspend fun insertMovies(movies: List<MovieEntity>): Int
        suspend fun getMovies(): List<MovieEntity>
        suspend fun getMoviesFromNetwork(): BaseMovieResponse<List<Movie>>
    }
}