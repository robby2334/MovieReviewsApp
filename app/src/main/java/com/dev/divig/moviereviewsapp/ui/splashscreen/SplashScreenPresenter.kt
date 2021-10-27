package com.dev.divig.moviereviewsapp.ui.splashscreen

import com.dev.divig.moviereviewsapp.base.BasePresenterImpl
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.model.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashScreenPresenter(
    private val view: SplashScreenContract.View,
    private val repository: SplashScreenContract.Repository
) : SplashScreenContract.Presenter, BasePresenterImpl() {
    override fun insertMovies(movies: List<MovieEntity>) {
        view.onDataCallback(Resource.Loading())
        scope.launch {
            try {
                val notes = repository.insertMovies(movies)
                scope.launch(Dispatchers.Main) {
                    if (notes > 0) {
                        view.onDataCallback(Resource.Success(true))
                    } else {
                        view.onDataCallback(Resource.Success(false))
                    }
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onDataCallback(Resource.Error(e.message.orEmpty()))
                }
            }
        }
    }

    override fun checkStateFirstRunApp() {
        if (repository.getStateFirstRunApp()) {
            view.navigateToIntroPage()
        } else {
            view.navigateToMainPage()
        }
    }
}