package com.dev.divig.moviereviewsapp.ui.detail

import android.content.Context
import android.content.Intent
import android.widget.Toast
import coil.load
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
        supportActionBar?.hide()
        getExtras()

        getViewBinding().ivBtnBack.setOnClickListener {
            onBackPressed()
        }
        getViewBinding().detailMovie.etReview.setOnClickListener {
            Toast.makeText(this, "Open Bottom Sheet Dialog", Toast.LENGTH_SHORT).show()
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
        val imgUrl = Constant.BASE_URL_IMAGE
        getViewBinding().imgCollapsing.load(imgUrl + movie.backdropPath)
        getViewBinding().detailMovie.ivProfileMovie.load(imgUrl + movie.posterPath)
        getViewBinding().detailMovie.tvTitleMovie.text = movie.title
        getViewBinding().detailMovie.tvGenre.text = movie.genres
        getViewBinding().detailMovie.tvDate.text = movie.releaseDate
        getViewBinding().detailMovie.tvRuntime.text = movie.runtime.toString()
        getViewBinding().detailMovie.tvOverview.text = movie.overview
    }

    companion object {
        fun startActivity(context: Context?, id: Int) {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(Constant.KEY_EXTRA_ID, id)
            }
            context?.startActivity(intent)
        }
    }
}



