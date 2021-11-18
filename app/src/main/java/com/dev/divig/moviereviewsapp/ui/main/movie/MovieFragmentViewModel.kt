package com.dev.divig.moviereviewsapp.ui.main.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.MovieEntity
import com.dev.divig.moviereviewsapp.utils.Utils
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

    override fun getMovies(update: Boolean) {
        repositoryLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var movies = repository.getMovies()
                if (movies.isEmpty() || update) {
                    val response = repository.getMoviesFromNetwork()

                    if (response.success == false) {
                        viewModelScope.launch(Dispatchers.Main) {
                            repositoryLiveData.value =
                                Resource.Error(response.statusMessage.orEmpty())
                        }
                    } else {
                        val movieList = ArrayList<MovieEntity>()
                        response.results.forEach { item ->
                            val genres = StringBuilder().append("")
                            item.genreIds?.forEachIndexed { index, value ->
                                if (index < item.genreIds.size - 1) {
                                    genres.append("${Utils.getGenreName(value)}, ")
                                } else {
                                    genres.append(Utils.getGenreName(value))
                                }
                            }

                            val movieEntity = MovieEntity(
                                item.id,
                                item.title,
                                item.overview,
                                genres.toString(),
                                item.releaseDate,
                                0,
                                item.voteAverage,
                                item.posterPath,
                                item.backdropPath
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