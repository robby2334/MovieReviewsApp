package com.dev.divig.moviereviewsapp.ui.main

import com.dev.divig.moviereviewsapp.base.BasePresenterImpl
import com.dev.divig.moviereviewsapp.base.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityPresenter(
    private val view: MainActivityContract.View,
    private val repository: MainActivityContract.Repository
) : MainActivityContract.Presenter, BasePresenterImpl() {

    override fun getMovies() {
        view.onDataCallback(Resource.Loading())
        scope.launch {
            try {
                delay(2000)
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