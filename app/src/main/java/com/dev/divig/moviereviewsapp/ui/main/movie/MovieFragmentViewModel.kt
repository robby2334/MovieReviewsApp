package com.dev.divig.moviereviewsapp.ui.main.movie

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
class MovieFragmentViewModel @Inject constructor(private val repository: MovieFragmentRepository) :
    ViewModel(),
    MovieFragmentContract.ViewModel {

    private val repositoryLiveData = MutableLiveData<Resource<List<MovieEntity>>>()

    override fun getMoviesLiveData(): LiveData<Resource<List<MovieEntity>>> = repositoryLiveData

    override fun getMovies() {
        repositoryLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var movies = repository.getMovies()
                if (movies.isEmpty()) {
                    val response = repository.getMoviesFromNetwork()

                    if (response.success == false) {
                        viewModelScope.launch(Dispatchers.Main) {
                            repositoryLiveData.value =
                                Resource.Error(response.statusMessage.orEmpty())
                        }
                    } else {
                        val movieList = ArrayList<MovieEntity>()
                        response.results.forEach { value ->
                            val movieEntity = MovieEntity(
                                value.id,
                                value.title,
                                value.overview,
                                null,
                                value.releaseDate,
                                0,
                                value.voteAverage,
                                value.posterPath,
                                value.backdropPath
                            )
                            movieList.add(movieEntity)
                        }
                        repository.insertMovies(movieList)
                    }
                    movies = repository.getMovies()
                }
                viewModelScope.launch(Dispatchers.Main) {
                    repositoryLiveData.value = Resource.Success(movies)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    repositoryLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }
}