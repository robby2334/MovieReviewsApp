package com.dev.divig.moviereviewsapp.ui.main

import com.dev.divig.moviereviewsapp.base.BaseContract
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.model.MovieEntity

interface MainActivityContract {
    interface View : BaseContract.BaseView {
        fun getMovies()
        fun onDataCallback(response: Resource<List<MovieEntity>>)
        fun setupRecyclerView(movies: List<MovieEntity>)
        fun setupBanner(movie: List<MovieEntity>)
    }

    interface Presenter : BaseContract.BasePresenter {
        fun getMovies()
    }

    interface Repository {
        suspend fun getMovies(): List<MovieEntity>
    }
}