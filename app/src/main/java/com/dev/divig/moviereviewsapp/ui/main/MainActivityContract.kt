package com.dev.divig.moviereviewsapp.ui.main

import com.dev.divig.moviereviewsapp.base.BaseContract
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.model.MovieEntity

interface MainActivityContract {
    interface View : BaseContract.BaseView {
        fun setupRecyclerView(movies: List<MovieEntity>)
        fun setClickListeners(movie: MovieEntity)
        fun onInsertDataCallback(response: Resource<Pair<Boolean, Int>>)
        fun onDataCallback(response: Resource<List<MovieEntity>>)
        fun insertMovies()
        fun getMovies()
        fun setupBanner()
    }

    interface Presenter : BaseContract.BasePresenter {
        fun insertMovies(movies: List<MovieEntity>)
        fun getMovies()
    }

    interface Repository {
        suspend fun insertMovies(movies: List<MovieEntity>)
        suspend fun getMovies(): List<MovieEntity>
    }

}