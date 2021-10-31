package com.dev.divig.moviereviewsapp.ui.detail

import com.dev.divig.moviereviewsapp.base.BasePresenterImpl
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.utils.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailPresenter(
    private val view: DetailContract.View,
    private val repository: DetailContract.Repository
) : DetailContract.Presenter, BasePresenterImpl() {
    override fun getMovie(id: Int) {
        view.onDataCallback(Resource.Loading())
        scope.launch {
            try {
                delay(2000)
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

    override fun getReviewsByMovieId(movieId: Int) {
        scope.launch {
            try {
                val reviews = repository.getReviewsByMovieId(movieId)
                scope.launch(Dispatchers.Main) {
                    if (reviews.isNotEmpty()) {
                        if (reviews.count { it.username == Constant.USERNAME } > 0) {
                            view.onGetReviewSuccess(reviews.filter { it.username == Constant.USERNAME }[0])
                        } else {
                            view.onGetReviewSuccess(reviews[0])
                        }
                    } else {
                        view.onDataReviewEmpty()
                    }
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onDataCallback(Resource.Error(e.message.orEmpty()))
                }
            }
        }
    }
}