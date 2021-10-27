package com.dev.divig.moviereviewsapp.ui.bottomsheetreview

import com.dev.divig.moviereviewsapp.base.BasePresenterImpl
import com.dev.divig.moviereviewsapp.base.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BottomSheetActivityPresenter(
    private val view: BottomSheetActivityContract.View,
    private val repository: BottomSheetActivityContract.Repository
) :
    BottomSheetActivityContract.Presenter, BasePresenterImpl() {
    override fun getAllReviews() {
        view.onDataCallback(Resource.Loading())
        scope.launch {
            try {
                delay(2000)
                val reviews = repository.getAllReviews()
                scope.launch(Dispatchers.Main) {
                    //check if data is empty
                    view.onDataCallback(Resource.Success(reviews))
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onDataCallback(Resource.Error(e.message.orEmpty()))
                }
            }
        }
    }

}