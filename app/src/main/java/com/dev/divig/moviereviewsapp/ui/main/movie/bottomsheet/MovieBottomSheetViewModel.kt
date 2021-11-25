package com.dev.divig.moviereviewsapp.ui.main.movie.bottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieBottomSheetViewModel @Inject constructor(private val repository: MovieBottomSheetRepository) :
    ViewModel(),
    MovieBottomSheetContract.ViewModel {

    private val moviesLiveData = MutableLiveData<Resource<List<MovieEntity>>>()

    override fun getMoviesLiveData(): LiveData<Resource<List<MovieEntity>>> = moviesLiveData

    override fun getMovies() {
        moviesLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movies = repository.getMovies()
                viewModelScope.launch(Dispatchers.Main) {
                    moviesLiveData.value = Resource.Success(movies)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    moviesLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }
}