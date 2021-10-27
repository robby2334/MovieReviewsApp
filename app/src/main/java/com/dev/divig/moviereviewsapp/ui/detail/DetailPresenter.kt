package com.dev.divig.moviereviewsapp.ui.detail

import com.dev.divig.moviereviewsapp.base.BasePresenterImpl
import com.dev.divig.moviereviewsapp.base.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailPresenter(
    private val view: DetailContract.View,
    private val repository: DetailContract.Repository
) : DetailContract.Presenter, BasePresenterImpl() {
    override fun getMovie(id: Int) {
        view.onDataCallback(Resource.Loading())
        scope.launch {
            try {
                val movie = repository.getMovie(id)
                scope.launch(Dispatchers.Main) {
                    view.onDataCallback(Resource.Success(movie))
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onDataCallback(Resource.Error(e.message.orEmpty()))
                }
            }
        }
    }
}