package com.dev.divig.moviereviewsapp.ui.splashscreen

import com.dev.divig.moviereviewsapp.base.BasePresenterImpl
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.model.MovieEntity
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashScreenPresenter(
    private val view: SplashScreenContract.View,
    private val repository: SplashScreenContract.Repository
) : SplashScreenContract.Presenter, BasePresenterImpl() {
    override fun insertData(movies: List<MovieEntity>, reviews: List<ReviewEntity>) {
        scope.launch {
            try {
                val listMovies = repository.insertMovies(movies)
                val listReviews = repository.insertAllReviews(reviews)
                scope.launch(Dispatchers.Main) {
                    if (listMovies > 0 && listReviews > 0) {
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

    override fun checkAvailabilityData(movieId: Int) {
        scope.launch {
            if (repository.getMovies().isEmpty() &&
                repository.getReviewsByMovieId(movieId).isEmpty()
            ) {
                scope.launch(Dispatchers.Main) {
                    view.insertData()
                }
            } else {
                scope.launch(Dispatchers.Main) {
                    view.setSplashScreenTimer()
                }
            }
        }
    }

    override fun checkStateFirstRunApp() {
        if (repository.isFirstRunApp()) {
            view.navigateToIntroPage()
        } else {
            view.navigateToMainPage()
        }
    }
}