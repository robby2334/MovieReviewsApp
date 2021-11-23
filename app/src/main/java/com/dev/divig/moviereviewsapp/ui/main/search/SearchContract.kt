package com.dev.divig.moviereviewsapp.ui.main.search

import androidx.lifecycle.LiveData
import com.dev.divig.moviereviewsapp.base.BaseContract
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.BaseMovieResponse
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.Movie

interface SearchContract {
    interface View : BaseContract.BaseView {
        fun initSearchView()
        fun searchMovies(query: String)
        fun setupRecyclerView(movies: List<Movie>)
        fun showScenarioPlaceholder(isVisible: Boolean, type: Int)
        fun setScenarioComponent(type: Int)
    }

    interface ViewModel {
        fun getMoviesLiveData(): LiveData<Resource<List<Movie>>>
        fun searchMovies(query: String)
    }

    interface Repository {
        suspend fun searchMovies(query: String): BaseMovieResponse<List<Movie>>
    }
}