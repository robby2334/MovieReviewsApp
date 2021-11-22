package com.dev.divig.moviereviewsapp.ui.main.movie.bottomsheet

import androidx.lifecycle.LiveData
import com.dev.divig.moviereviewsapp.base.BaseContract
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.MovieEntity

interface MovieBottomSheetContract {
    interface View : BaseContract.BaseView {
        fun getMovies()
        fun setupRecyclerView(movies: List<MovieEntity>)
    }

    interface ViewModel {
        fun getMoviesLiveData(): LiveData<Resource<List<MovieEntity>>>
        fun getMovies()
    }

    interface Repository {
        suspend fun getMovies(): List<MovieEntity>
    }
}