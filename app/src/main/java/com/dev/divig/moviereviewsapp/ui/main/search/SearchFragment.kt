package com.dev.divig.moviereviewsapp.ui.main.search

import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import coil.load
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseFragment
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.Movie
import com.dev.divig.moviereviewsapp.databinding.FragmentSearchBinding
import com.dev.divig.moviereviewsapp.ui.detail.DetailActivity
import com.dev.divig.moviereviewsapp.ui.main.search.adapter.SearchAdapter
import com.dev.divig.moviereviewsapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    SearchContract.View {

    private val viewModel: SearchViewModel by viewModels()

    override fun initView() {
        showLoading(false)
        initSearchView()
        showScenarioPlaceholder(isVisible = true, isEmptySearch = false)
    }

    override fun observeViewModel() {
        viewModel.getMoviesLiveData().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    showScenarioPlaceholder(isVisible = false, isEmptySearch = true)
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    response.data?.let {
                        if (it.isEmpty()) {
                            showScenarioPlaceholder(isVisible = true, isEmptySearch = true)
                        } else {
                            setupRecyclerView(it)
                        }
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showScenarioPlaceholder(isVisible = false, isEmptySearch = true)
                    showError(true, response.message)
                }
            }
        }
    }

    override fun initSearchView() {
        getViewBinding().svMovies.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    return true
                } else {
                    setupRecyclerView(emptyList())
                    searchMovies(query)
                    Utils.hideSoftKeyboard(requireActivity(), getViewBinding().svMovies)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    override fun searchMovies(query: String) {
        viewModel.searchMovies(query)
    }

    override fun setupRecyclerView(movies: List<Movie>) {
        val searchAdapter = SearchAdapter {
            navigateToDetail(it)
        }
        searchAdapter.submitList(movies)
        getViewBinding().rvMovie.apply {
            adapter = searchAdapter
        }
    }

    private fun navigateToDetail(movie: Movie) {
        DetailActivity.startActivity(requireContext(), movie.id, true)
    }

    override fun showScenarioPlaceholder(isVisible: Boolean, isEmptySearch: Boolean) {
        getViewBinding().rvMovie.isGone = isVisible
        setScenarioComponent(isEmptySearch)
        getViewBinding().layoutScenario.layoutComponentScenario.isVisible = isVisible
    }

    override fun setScenarioComponent(isEmptySearch: Boolean) {
        val imgDrawable: Int
        val title: String
        val desc: String
        if (isEmptySearch) {
            imgDrawable = R.drawable.ic_no_search_results
            title = getString(R.string.text_title_no_search_result)
            desc = getString(R.string.message_no_search_result)
        } else {
            imgDrawable = R.drawable.ic_search_movies
            title = getString(R.string.text_title_search_movies)
            desc = getString(R.string.message_search_movies)
        }

        getViewBinding().layoutScenario.ivScenario.load(imgDrawable)
        getViewBinding().layoutScenario.tvTitle.text = title
        getViewBinding().layoutScenario.tvDesc.text = desc
    }

    override fun showLoading(isLoading: Boolean) {
        super.showLoading(isLoading)
        getViewBinding().sflSearchPlaceholder.isVisible = isLoading
        if (isLoading) {
            getViewBinding().sflSearchPlaceholder.startShimmer()
        } else {
            getViewBinding().sflSearchPlaceholder.stopShimmer()
        }
    }
}