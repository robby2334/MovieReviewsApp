package com.dev.divig.moviereviewsapp.ui.main.favorite

import androidx.lifecycle.LiveData
import com.dev.divig.moviereviewsapp.base.BaseContract
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.MovieEntity

interface FavoriteContract {
    interface View : BaseContract.BaseView {
        fun getFavMovies()
        fun setupRecyclerView(movies: List<MovieEntity>)
    }

    interface ViewModel {
        fun getFavoriteLiveData(): LiveData<Resource<List<MovieEntity>>>
        fun getFavMovies()
    }

    interface Repository {
        suspend fun getFavMovies(): List<MovieEntity>
    }
}