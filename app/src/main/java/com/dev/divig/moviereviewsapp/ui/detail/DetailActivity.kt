package com.dev.divig.moviereviewsapp.ui.detail

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseActivity
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.MovieEntity
import com.dev.divig.moviereviewsapp.data.local.model.ReviewEntity
import com.dev.divig.moviereviewsapp.databinding.ActivityDetailBinding
import com.dev.divig.moviereviewsapp.ui.detail.bottomsheetreview.ReviewsBottomSheet
import com.dev.divig.moviereviewsapp.utils.Constant
import com.dev.divig.moviereviewsapp.utils.FullScreenHelper
import com.dev.divig.moviereviewsapp.utils.StringUtils.textFromHtml
import com.dev.divig.moviereviewsapp.utils.Utils
import com.google.android.material.appbar.AppBarLayout
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity :
    BaseActivity<ActivityDetailBinding, DetailViewModel>(ActivityDetailBinding::inflate),
    DetailContract.View, AppBarLayout.OnOffsetChangedListener {
    private lateinit var movie: MovieEntity
    private lateinit var youtubePlayerView: YouTubePlayerView
    private lateinit var fullScreenHelper: FullScreenHelper

    override val viewModelInstance: DetailViewModel by viewModels()
    private val youtubeTracker = YouTubePlayerTracker()
    private var movieId: Int = 0
    private var isStartMovie = false
    private var isPlayingMovie = false

    override fun initView() {
        supportActionBar?.hide()
        youtubePlayerView = getViewBinding().youtubePlayerView
        fullScreenHelper = FullScreenHelper(this)
        getViewBinding().appBarLayout.addOnOffsetChangedListener(this)
        getExtras()
        observeViewModel()
        initScenarioComponent()
        setClickListeners()
    }

    override fun observeViewModel() {
        getViewModel().getMovieLiveData().observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showContent(true)
                    response.data?.let {
                        movie = it
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

        getViewModel().getReviewLiveData().observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    showEmptyPlaceholder(false)
                    if (response.data != null) {
                        fetchDataReview(response.data)
                    } else {
                        showEmptyPlaceholder(true)
                    }
                }
                is Resource.Error -> {
                    showError(true, response.message)
                }
            }
        }
    }

    override fun initScenarioComponent() {
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
        getViewBinding().detailMovie.tvBtnShowAllReview.setOnClickListener {
            openReviewsBottomSheet()
        }
        getViewBinding().detailMovie.itemReview.layoutItemReview.setOnClickListener {
            openReviewsBottomSheet()
        }
        getViewBinding().fabAddToFavorite.setOnClickListener {
            setFavoriteMovie()
        }
    }

    private fun getExtras() {
        val id = intent.getIntExtra(Constant.KEY_EXTRA_ID, 0)
        val isSearch = intent.getBooleanExtra(Constant.KEY_EXTRA_IS_SEARCH, false)
        movieId = id
        getMovieDetail(movieId, isSearch)
    }

    override fun getMovieDetail(movieId: Int, isSearch: Boolean) {
        if (checkInternetConnection()) {
            getMovie(movieId, isSearch, true)
            getReviewsByMovieId(movieId, true)
        } else {
            Utils.noInternetDialog(
                this,
                getString(R.string.text_placeholder_close),
                {
                    getMovieDetail(movieId, isSearch)
                },
                {
                    it?.dismiss()
                    if (isSearch) {
                        finish()
                    } else {
                        getMovie(movieId, isSearch, false)
                        getReviewsByMovieId(movieId, false)
                    }
                }
            )
        }
    }

    override fun getMovie(id: Int, isSearch: Boolean, update: Boolean) {
        getViewModel().getMovie(id, isSearch, update)
    }

    override fun getReviewsByMovieId(id: Int, update: Boolean) {
        getViewModel().getReviewsByMovieId(id, update)
    }

    override fun setFavoriteMovie() {
        getViewModel().setFavoriteMovie(movie)
    }

    override fun enterFullScreen() {
        fullScreenHelper.enterFullScreen()
    }

    override fun exitFullScreen() {
        fullScreenHelper.exitFullScreen()
    }

    override fun fetchDataMovie(movie: MovieEntity) {
        setFabFavorite(movie.isFavorite)

        val imgBackdrop = Constant.BASE_URL_IMAGE + movie.backdropPath
        getViewBinding().imgBackdropMovie.load(imgBackdrop) {
            error(R.color.color_secondary_variant)
        }
        movie.videoKey?.let {
            setupVideoTrailer(it)
        }

        val imgPoster = Constant.BASE_URL_IMAGE + movie.posterPath
        getViewBinding().detailMovie.ivPoster.load(imgPoster) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
        getViewBinding().detailMovie.tvTitleMovie.text = movie.title
        getViewBinding().detailMovie.tvRating.text = movie.voteAverage.toString()
        getViewBinding().detailMovie.ratingBar.rating =
            (movie.voteAverage?.div(2))?.toFloat() ?: Constant.ZERO_FLOAT
        getViewBinding().detailMovie.tvDate.text = movie.releaseDate
        getViewBinding().detailMovie.tvRuntime.text = Utils.convertRuntime(movie.runtime ?: 0)
        getViewBinding().detailMovie.tvGenre.text = movie.genres
        getViewBinding().detailMovie.tvOverview.text = movie.overview
    }

    override fun fetchDataReview(review: ReviewEntity?) {
        getViewBinding().detailMovie.itemReview.tvReviewName.text = review?.author
        getViewBinding().detailMovie.itemReview.tvDescReview.textFromHtml(review?.content)
        getViewBinding().detailMovie.itemReview.tvDescReview.isClickable = false
        getViewBinding().detailMovie.itemReview.tvDateReview.text =
            Utils.dateFormatter(review?.createAt)
    }

    private fun openReviewsBottomSheet() {
        ReviewsBottomSheet(movieId, youtubePlayerView.isFullScreen()).show(
            supportFragmentManager,
            null
        )
    }

    private fun setupVideoTrailer(videoId: String) {
        lifecycle.addObserver(youtubePlayerView)

        youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(videoId, 0f)
                addFullScreenListenerToPlayer()
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                when (state) {
                    PlayerConstants.PlayerState.PLAYING -> {
                        getViewBinding().ivBtnBack.isVisible = false
                        isStartMovie = true
                        isPlayingMovie = false
                    }
                    PlayerConstants.PlayerState.PAUSED -> {
                        getViewBinding().ivBtnBack.isVisible = true
                        if (youtubePlayerView.isFullScreen()) {
                            getViewBinding().fabAddToFavorite.isVisible = false
                        }
                        isStartMovie = false
                    }
                    PlayerConstants.PlayerState.UNSTARTED -> {
                        youtubePlayerView.isVisible = true
                        getViewBinding().imgBackdropMovie.isVisible = false
                    }
                    else -> {
                        getViewBinding().ivBtnBack.isVisible = true
                    }
                }
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                youTubePlayer.removeListener(youtubeTracker)
                youtubePlayerView.isVisible = false
                getViewBinding().imgBackdropMovie.isVisible = true
            }
        })
        youtubePlayerView.addYouTubePlayerListener(youtubeTracker)
    }

    private fun addFullScreenListenerToPlayer() {
        youtubePlayerView.addFullScreenListener(object :
            YouTubePlayerFullScreenListener {
            override fun onYouTubePlayerEnterFullScreen() {
                getViewBinding().appBarLayout.layoutParams.apply {
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                    height = ViewGroup.LayoutParams.MATCH_PARENT
                }
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                enterFullScreen()
                addCustomActionsToPlayer()
            }

            override fun onYouTubePlayerExitFullScreen() {
                getViewBinding().fabAddToFavorite.isVisible = true
                getViewBinding().appBarLayout.layoutParams.apply {
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                    height = resources.getDimension(R.dimen._170sdp).toInt()
                }
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                exitFullScreen()
                removeCustomActionsFromPlayer()
            }
        })
    }

    private fun addCustomActionsToPlayer() {
        val customAction1Icon =
            ContextCompat.getDrawable(this, R.drawable.ic_fast_rewind_white_24)
        val customAction2Icon =
            ContextCompat.getDrawable(this, R.drawable.ic_fast_forward_white_24)

        youtubePlayerView.getYouTubePlayerWhenReady(object :
            YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youtubePlayerView.getPlayerUiController()
                    .setCustomAction1(customAction1Icon as Drawable) {
                        youTubePlayer.loadOrCueVideo(
                            lifecycle,
                            youtubeTracker.videoId.orEmpty(),
                            (youtubeTracker.currentSecond - Constant.TEEN)
                        )
                    }
                youtubePlayerView.getPlayerUiController()
                    .setCustomAction2(customAction2Icon as Drawable) {
                        youTubePlayer.loadOrCueVideo(
                            lifecycle,
                            youtubeTracker.videoId.orEmpty(),
                            (youtubeTracker.currentSecond + Constant.TEEN)
                        )
                    }
            }
        })
    }

    private fun removeCustomActionsFromPlayer() {
        youtubePlayerView.getPlayerUiController().showCustomAction1(false)
        youtubePlayerView.getPlayerUiController().showCustomAction2(false)
    }

    private fun setFabFavorite(state: Boolean) {
        if (state) {
            getViewBinding().fabAddToFavorite.load(R.drawable.ic_favorite_filled_24)
        } else {
            getViewBinding().fabAddToFavorite.load(R.drawable.ic_favorite_outlined_24)
        }
    }

    override fun showEmptyPlaceholder(isVisible: Boolean) {
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
        if (msg.isNullOrEmpty().not()) showSnackBarError(msg)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        youtubePlayerView.getYouTubePlayerWhenReady(object :
            YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                if (verticalOffset == 0) {
                    if (youtubePlayerView.isFullScreen()) {
                        getViewBinding().fabAddToFavorite.isVisible = false
                        enterFullScreen()
                    }
                    if (isPlayingMovie) {
                        youTubePlayer.play()
                    }
                } else {
                    if (youtubePlayerView.isFullScreen()) {
                        getViewBinding().fabAddToFavorite.isVisible = true
                    }
                    if (isStartMovie) {
                        isPlayingMovie = true
                    }
                    youTubePlayer.pause()
                }
            }
        })
    }

    override fun onBackPressed() {
        if (youtubePlayerView.isFullScreen()) {
            youtubePlayerView.exitFullScreen()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        fun startActivity(context: Context?, id: Int, isSearch: Boolean) {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(Constant.KEY_EXTRA_ID, id)
                putExtra(Constant.KEY_EXTRA_IS_SEARCH, isSearch)
            }
            context?.startActivity(intent)
        }
    }
}