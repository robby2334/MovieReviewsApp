package com.dev.divig.moviereviewsapp.ui.main.favorite

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import coil.load
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseFragment
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.MovieEntity
import com.dev.divig.moviereviewsapp.databinding.FragmentFavoriteBinding
import com.dev.divig.moviereviewsapp.ui.detail.DetailActivity
import com.dev.divig.moviereviewsapp.ui.main.adapter.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate),
    FavoriteContract.View {

    private val viewModel: FavoriteViewModel by viewModels()

    override fun initView() {
        initScenarioComponent()
        observeViewModel()
    }

    private fun initScenarioComponent() {
        getViewBinding().layoutScenario.ivScenario.load(R.drawable.ic_favorite_placeholder)
        getViewBinding().layoutScenario.tvTitle.text =
            getString(R.string.text_title_favorite_placeholder)
        getViewBinding().layoutScenario.tvDesc.text =
            getString(R.string.message_favorite_placeholder)
    }

    override fun observeViewModel() {
        viewModel.getFavoriteLiveData().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    showEmptyPlaceholder(false)
                }
                is Resource.Success -> {
                    response.data?.let {
                        if (it.isEmpty()) {
                            showEmptyPlaceholder(true)
                        } else {
                            setupRecyclerView(it)
                        }
                    }
                }
                is Resource.Error -> {
                    showEmptyPlaceholder(false)
                    showError(true, response.message)
                }
            }
        }
    }

    override fun getFavMovies() {
        viewModel.getFavMovies()
    }

    override fun setupRecyclerView(movies: List<MovieEntity>) {
        val moviesAdapter = MoviesAdapter {
            navigateToDetail(it)
        }
        moviesAdapter.submitList(movies)
        getViewBinding().rvMovie.apply {
            adapter = moviesAdapter
        }
    }

    private fun navigateToDetail(movie: MovieEntity) {
        DetailActivity.startActivity(requireContext(), movie.id, false)
    }

    private fun showEmptyPlaceholder(isVisible: Boolean) {
        getViewBinding().layoutScenario.layoutComponentScenario.isVisible = isVisible
        getViewBinding().rvMovie.isGone = isVisible
    }

    override fun onResume() {
        super.onResume()
        getFavMovies()
    }
}