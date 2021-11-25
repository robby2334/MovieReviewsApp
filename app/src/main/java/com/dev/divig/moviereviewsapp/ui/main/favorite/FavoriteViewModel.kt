package com.dev.divig.moviereviewsapp.ui.main.favorite

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
class FavoriteViewModel @Inject constructor(private val repository: FavoriteRepository) :
    ViewModel(),
    FavoriteContract.ViewModel {

    private val favoriteLiveData = MutableLiveData<Resource<List<MovieEntity>>>()

    override fun getFavoriteLiveData(): LiveData<Resource<List<MovieEntity>>> = favoriteLiveData

    override fun getFavMovies() {
        favoriteLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movies = repository.getFavMovies()
                viewModelScope.launch(Dispatchers.Main) {
                    favoriteLiveData.value = Resource.Success(movies)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    favoriteLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }
}