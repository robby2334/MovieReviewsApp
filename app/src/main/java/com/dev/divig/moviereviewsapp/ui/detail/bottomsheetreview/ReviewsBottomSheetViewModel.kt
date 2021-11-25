package com.dev.divig.moviereviewsapp.ui.detail.bottomsheetreview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.ReviewEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsBottomSheetViewModel @Inject constructor(private val repository: ReviewsBottomSheetRepository) :
    ViewModel(), ReviewsBottomSheetContract.ViewModel {

    private val reviewsRepositoryLiveData = MutableLiveData<Resource<List<ReviewEntity>>>()

    override fun getReviewsLiveData(): LiveData<Resource<List<ReviewEntity>>> =
        reviewsRepositoryLiveData

    override fun getReviewsByMovieId(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val reviews = repository.getReviewsByMovieId(movieId)
                viewModelScope.launch(Dispatchers.Main) {
                    if (reviews.isNotEmpty()) {
                        reviewsRepositoryLiveData.value = Resource.Success(reviews)
                    } else {
                        reviewsRepositoryLiveData.value = Resource.Success(emptyList())
                    }
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    reviewsRepositoryLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }
}