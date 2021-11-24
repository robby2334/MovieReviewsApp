package com.dev.divig.moviereviewsapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.MovieEntity
import com.dev.divig.moviereviewsapp.data.local.model.ReviewEntity
import com.dev.divig.moviereviewsapp.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: DetailRepository) : ViewModel(),
    DetailContract.ViewModel {

    private val movieRepositoryLiveData = MutableLiveData<Resource<MovieEntity>>()
    private val reviewRepositoryLiveData = MutableLiveData<Resource<ReviewEntity?>>()

    override fun getMovieLiveData(): LiveData<Resource<MovieEntity>> = movieRepositoryLiveData

    override fun getReviewLiveData(): LiveData<Resource<ReviewEntity?>> = reviewRepositoryLiveData

    override fun setFavoriteMovie(movie: MovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val newState = !movie.isFavorite
            repository.setFavoriteMovie(movie, newState)
            val movieData = repository.getDetailMovie(movie.id)
            viewModelScope.launch(Dispatchers.Main) {
                movieRepositoryLiveData.value = Resource.Success(movieData)
            }
        }
    }

    override fun getMovie(id: Int, isSearch: Boolean, update: Boolean) {
        movieRepositoryLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var movie = repository.getDetailMovie(id)
                if (isSearch || update) {
                    val response = repository.getDetailMovieFromNetwork(id)

                    val genres = StringBuilder().append("")
                    response.genres.forEachIndexed { index, item ->
                        if (index < response.genres.size - 1) {
                            genres.append("${item.name}, ")
                        } else {
                            genres.append(item.name)
                        }
                    }

                    if (response.success == false) {
                        viewModelScope.launch(Dispatchers.Main) {
                            movieRepositoryLiveData.value =
                                Resource.Error(response.statusMessage.orEmpty())
                        }
                    } else {
                        val listVideos = response.videos?.results
                        val videoKey = if (!listVideos.isNullOrEmpty()) {
                            listVideos.filter { it.type == Constant.TRAILER }.let {
                                it[0].key
                            }
                        } else null

                        val movieEntity = MovieEntity(
                            id,
                            response.title,
                            response.overview,
                            genres.toString(),
                            response.releaseDate,
                            response.runtime,
                            response.voteAverage,
                            response.posterPath,
                            response.backdropPath,
                            videoKey,
                            movie.isFavorite
                        )
                        if (isSearch) {
                            repository.insertMovie(movieEntity)
                        } else {
                            repository.updateMovie(movieEntity)
                        }
                    }
                    movie = repository.getDetailMovie(id)
                }
                viewModelScope.launch(Dispatchers.Main) {
                    movieRepositoryLiveData.value = Resource.Success(movie)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    movieRepositoryLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }

    override fun getReviewsByMovieId(movieId: Int, update: Boolean) {
        reviewRepositoryLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var reviews = repository.getReviews(movieId)
                if (update) {
                    val response = repository.getReviewsFromNetwork(movieId)

                    if (response.success == false) {
                        viewModelScope.launch(Dispatchers.Main) {
                            reviewRepositoryLiveData.value =
                                Resource.Error(response.statusMessage.orEmpty())
                        }
                    } else {
                        val reviewList = ArrayList<ReviewEntity>()
                        response.results.forEach { value ->
                            val reviewEntity = ReviewEntity(
                                value.id,
                                movieId,
                                value.author,
                                value.content,
                                value.createdAt
                            )
                            reviewList.add(reviewEntity)
                        }
                        repository.insertReviews(reviewList)
                    }
                    reviews = repository.getReviews(movieId)
                }
                viewModelScope.launch(Dispatchers.Main) {
                    if (reviews.isNotEmpty()) {
                        reviewRepositoryLiveData.value = Resource.Success(reviews[0])
                    } else {
                        reviewRepositoryLiveData.value =
                            Resource.Success(null)
                    }
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    reviewRepositoryLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }
}