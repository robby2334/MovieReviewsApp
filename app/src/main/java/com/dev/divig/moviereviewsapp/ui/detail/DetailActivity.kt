package com.dev.divig.moviereviewsapp.ui.detail

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseActivity
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.room.MoviesDatabase
import com.dev.divig.moviereviewsapp.data.local.room.datasource.MoviesDataSourceImpl
import com.dev.divig.moviereviewsapp.data.model.MovieEntity
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity
import com.dev.divig.moviereviewsapp.databinding.ActivityDetailBinding
import com.dev.divig.moviereviewsapp.ui.detail.bottomsheetreview.ReviewsBottomSheet
import com.dev.divig.moviereviewsapp.utils.Constant
import com.dev.divig.moviereviewsapp.utils.Utils

class DetailActivity :
    BaseActivity<ActivityDetailBinding, DetailContract.Presenter>(ActivityDetailBinding::inflate),
    DetailContract.View {
    private var movieId: Int = 0

    override fun initView() {
        supportActionBar?.hide()
        getExtras()
        initScenarioComponent()
        setClickListeners()
    }

    override fun initPresenter() {
        val dataSource = MoviesDataSourceImpl(MoviesDatabase.getInstance(this).moviesDao())
        val repository = DetailRepository(dataSource)
        setPresenter(DetailPresenter(this, repository))
    }

    private fun initScenarioComponent() {
        getViewBinding().detailMovie.layoutScenario.ivScenario.load(R.drawable.ic_no_reviews_placeholder)
        getViewBinding().detailMovie.layoutScenario.tvTitle.text =
            getString(R.string.text_title_no_review)
        getViewBinding().detailMovie.layoutScenario.tvDesc.text =
            getString(R.string.message_no_review)
    }

    private fun setClickListeners() {
        getViewBinding().ivBtnBack.setOnClickListener {
            onBackPressed()
        }
        getViewBinding().detailMovie.etReview.setOnClickListener {
            openReviewsBottomSheet()
        }
        getViewBinding().detailMovie.itemReview.layoutItemReview.setOnClickListener {
            openReviewsBottomSheet()
        }
    }

    override fun onDataCallback(response: Resource<MovieEntity>) {
        when (response) {
            is Resource.Loading -> {
                showLoading(true)
                showContent(false)
            }
            is Resource.Success -> {
                showLoading(false)
                showContent(true)
                response.data?.let {
                    fetchDataMovie(it)
                }
            }
            is Resource.Error -> {
                showLoading(false)
                showContent(true)
                showError(true, response.message)
            }
        }
    }

    override fun onGetReviewSuccess(response: ReviewEntity) {
        showEmptyPlaceholder(false)
        fetchDataReview(response)
    }

    override fun onDataReviewEmpty() {
        showEmptyPlaceholder(true)
    }

    private fun getExtras() {
        val id = intent.getIntExtra(Constant.KEY_EXTRA_ID, 0)
        movieId = id
        getMovieDetail(id)
        getReviewByMovieId(movieId)
    }

    private fun getMovieDetail(id: Int) {
        getPresenter().getMovie(id)
    }

    private fun getReviewByMovieId(movieId: Int) {
        getPresenter().getReviewsByMovieId(movieId)
    }

    private fun fetchDataMovie(movie: MovieEntity) {
        val imgBackdrop =
            (Constant.BASE_URL_IMAGE + movie.backdropPath).toUri().buildUpon().scheme("https")
                .build()
        getViewBinding().imgCollapsing.load(imgBackdrop) {
            placeholder(R.color.color_secondary_variant)
            error(R.drawable.ic_broken_image)
        }
        val imgPoster =
            (Constant.BASE_URL_IMAGE + movie.posterPath).toUri().buildUpon().scheme("https")
                .build()
        getViewBinding().detailMovie.ivPoster.load(imgPoster) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
        getViewBinding().detailMovie.tvTitleMovie.text = movie.title
        getViewBinding().detailMovie.tvGenre.text = movie.genres
        getViewBinding().detailMovie.tvDate.text = movie.releaseDate
        getViewBinding().detailMovie.tvRuntime.text = Utils.convertRuntime(movie.runtime)
        getViewBinding().detailMovie.tvOverview.text = movie.overview
    }

    private fun fetchDataReview(review: ReviewEntity) {
        getViewBinding().detailMovie.itemReview.tvReviewName.text = review.author
        getViewBinding().detailMovie.itemReview.tvDescReview.text = review.content
        getViewBinding().detailMovie.itemReview.tvDescReview.isClickable = false
        getViewBinding().detailMovie.itemReview.tvDateReview.text =
            Utils.dateFormatter(review.createAt)
    }

    private fun openReviewsBottomSheet() {
        ReviewsBottomSheet(movieId) {
            getReviewByMovieId(movieId)
        }.show(supportFragmentManager, null)
    }

    private fun showEmptyPlaceholder(isVisible: Boolean) {
        getViewBinding().detailMovie.layoutScenario.layoutComponentScenario.isVisible = isVisible
        getViewBinding().detailMovie.itemReview.layoutItemReview.isGone = isVisible
    }

    override fun showContent(isContentVisible: Boolean) {
        super.showContent(isContentVisible)
        getViewBinding().detailMovie.layoutDetailMovie.isVisible = isContentVisible
    }

    override fun showLoading(isLoading: Boolean) {
        super.showLoading(isLoading)
        getViewBinding().sflDetailPlaceholder.isVisible = isLoading
        if (isLoading) {
            getViewBinding().sflDetailPlaceholder.startShimmer()
        } else {
            getViewBinding().sflDetailPlaceholder.stopShimmer()
        }
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        super.showError(isErrorEnabled, msg)
        if (isErrorEnabled) Toast.makeText(this, msg.orEmpty(), Toast.LENGTH_SHORT)
            .show()
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



