package com.dev.divig.moviereviewsapp.ui.main

import android.util.Log
import com.dev.divig.moviereviewsapp.base.BasePresenterImpl
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.model.MovieEntity
import com.dev.divig.moviereviewsapp.utils.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityPresenter(
    private val view: MainActivityContract.View,
    private val repository: MainActivityContract.Repository
) : MainActivityContract.Presenter, BasePresenterImpl() {

    override fun insertMovies(movies: List<MovieEntity>) {
        view.onDataCallback(Resource.Loading())
        scope.launch {
            try {
                repository.insertMovies(movies)
                scope.launch(Dispatchers.Main) {
                    view.onInsertDataCallback(Resource.Success(Pair(true, Constant.ACTION_INSERT)))
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onInsertDataCallback(Resource.Error(e.message.orEmpty()))
                }
            }
        }
    }

    override fun getMovies() {
        view.onDataCallback(Resource.Loading())
        scope.launch {
            try {
                val movies = repository.getMovies()
                scope.launch(Dispatchers.Main) {
                    view.onDataCallback(Resource.Success(movies))
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onDataCallback(Resource.Error(e.message.orEmpty()))
                }
            }
        }
    }

}