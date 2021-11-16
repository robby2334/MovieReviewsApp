package com.dev.divig.moviereviewsapp.ui.main.movie

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseFragment
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.MovieEntity
import com.dev.divig.moviereviewsapp.databinding.FragmentMovieBinding
import com.dev.divig.moviereviewsapp.ui.about.AboutActivity
import com.dev.divig.moviereviewsapp.ui.detail.DetailActivity
import com.dev.divig.moviereviewsapp.ui.main.movie.adapter.MovieAdapter
import com.dev.divig.moviereviewsapp.utils.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : BaseFragment<FragmentMovieBinding>(FragmentMovieBinding::inflate),
    MovieFragmentContract.View {

    private val viewModel: MovieFragmentViewModel by viewModels()

    override fun initView() {
        getMovies()
        setupAppbar()
    }

    override fun observeViewModel() {
        viewModel.getMoviesLiveData().observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                }
                is Resource.Success -> {
                    showLoading(false)
                    response.data?.let {
                        if (it.isEmpty()) {
                            showError(true, getString(R.string.message_empty_movies))
                            showContent(false)
                        } else {
                            showContent(true)
                            setupRecyclerView(it)
                            setupBanner(it)
                        }
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showError(true, response.message)
                    showContent(false)
                }
            }
        }
    }

    override fun getMovies() {
        viewModel.getMovies()
    }

    override fun setupRecyclerView(movies: List<MovieEntity>) {
        val adapter = MovieAdapter(
            MovieAdapter.OnClickListener {
                navigateToDetail(it)
            }
        )
        adapter.submitList(movies)
        getViewBinding().rvMovie.adapter = adapter
    }

    private fun navigateToDetail(movie: MovieEntity) {
        DetailActivity.startActivity(requireContext(), movie.id)
    }

    private fun navigateToAbout() {
        AboutActivity.startActivity(requireContext())
    }

    override fun setupBanner(movie: List<MovieEntity>) {
        val imageList = ArrayList<SlideModel>().apply {
            add(SlideModel(Constant.BASE_URL_IMAGE + movie[0].backdropPath, ""))
            add(SlideModel(Constant.BASE_URL_IMAGE + movie[2].backdropPath, ""))
            add(SlideModel(Constant.BASE_URL_IMAGE + movie[4].backdropPath, ""))
            add(SlideModel(Constant.BASE_URL_IMAGE + movie[6].backdropPath, ""))
            add(SlideModel(Constant.BASE_URL_IMAGE + movie[8].backdropPath, ""))
        }
        getViewBinding().imgSlider.setImageList(imageList, ScaleTypes.FIT)
    }

    private fun setupAppbar() {
        getViewBinding().toolbarMovie.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_about -> {
                    navigateToAbout()
                    true
                }
                else -> true
            }
        }
    }

    override fun showContent(isContentVisible: Boolean) {
        getViewBinding().rvMovie.isVisible = isContentVisible
    }

    override fun showLoading(isLoading: Boolean) {
        getViewBinding().sflMainPlaceholder.isVisible = isLoading
        if (isLoading) {
            getViewBinding().sflMainPlaceholder.startShimmer()
        } else {
            getViewBinding().sflMainPlaceholder.stopShimmer()
        }
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        if (isErrorEnabled) Toast.makeText(requireContext(), msg.orEmpty(), Toast.LENGTH_SHORT)
            .show()
    }
}