package com.dev.divig.moviereviewsapp.ui.detail

import android.widget.Toast
import com.dev.divig.moviereviewsapp.base.BaseActivity
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.room.MoviesDatabase
import com.dev.divig.moviereviewsapp.data.local.room.datasource.MoviesDataSourceImpl
import com.dev.divig.moviereviewsapp.data.model.MovieEntity
import com.dev.divig.moviereviewsapp.databinding.ActivityDetailBinding
import com.dev.divig.moviereviewsapp.utils.Constant

class DetailActivity :
    BaseActivity<ActivityDetailBinding, DetailContract.Presenter>(ActivityDetailBinding::inflate),
    DetailContract.View {
    override fun initView() {
        getExtras()
        getViewBinding().ivBtnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun initPresenter() {
        val dataSource = MoviesDataSourceImpl(MoviesDatabase.getInstance(this).moviesDao())
        val repository = DetailRepository(dataSource)
        setPresenter(DetailPresenter(this, repository))
    }

    override fun onDataCallback(response: Resource<MovieEntity>) {
        when (response) {
            is Resource.Loading -> {
            }
            is Resource.Success -> {
                response.data?.let {
                    fetchData(it)
                }
            }
            is Resource.Error -> {
                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getExtras() {
        val id = intent.getIntExtra(Constant.KEY_EXTRA_ID, 0)
        getMovieDetail(id)
    }

    override fun getMovieDetail(id: Int) {
        getPresenter().getMovie(id)
    }

    override fun fetchData(movie: MovieEntity) {
        getViewBinding().detailMovie.tvTitleMovie.text = movie.title
        getViewBinding().imgCollapsing.setImageResource(movie.backdropPath!!.toInt())
        getViewBinding().detailMovie.ivProfileMovie.setImageResource(movie.posterPath!!.toInt())
        getViewBinding().detailMovie.tvGenre.text = movie.genres
        getViewBinding().detailMovie.tvDate.text = movie.releaseDate
        getViewBinding().detailMovie.tvRuntime.text = movie.runtime.toString()
        getViewBinding().detailMovie.tvOverview.text = movie.overview
    }
}



