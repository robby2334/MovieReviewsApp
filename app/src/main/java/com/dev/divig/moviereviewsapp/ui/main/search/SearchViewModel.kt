package com.dev.divig.moviereviewsapp.ui.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) : ViewModel(),
    SearchContract.ViewModel {

    private val searchLiveData = MutableLiveData<Resource<List<Movie>>>()

    override fun getMoviesLiveData(): LiveData<Resource<List<Movie>>> = searchLiveData

    override fun searchMovies(query: String) {
        searchLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.searchMovies(query)
                if (response.success == false) {
                    viewModelScope.launch(Dispatchers.Main) {
                        searchLiveData.value =
                            Resource.Error(response.statusMessage.orEmpty())
                    }
                } else {
                    viewModelScope.launch(Dispatchers.Main) {
                        searchLiveData.value = Resource.Success(response.results)
                    }
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    searchLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }
}