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
import com.dev.divig.moviereviewsapp.utils.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment :
    BaseFragment<FragmentSearchBinding, SearchViewModel>(FragmentSearchBinding::inflate),
    SearchContract.View {
    private lateinit var query: String
    override val viewModelInstance: SearchViewModel by viewModels()

    override fun initView() {
        showLoading(false)
        initSearchView()
        showScenarioPlaceholder(isVisible = true, Constant.TYPE_PLACEHOLDER)
    }

    override fun observeViewModel() {
        getViewModel().getMoviesLiveData().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    showScenarioPlaceholder(isVisible = false, Constant.TYPE_EMPTY)
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    response.data?.let {
                        if (it.isEmpty()) {
                            showScenarioPlaceholder(isVisible = true, Constant.TYPE_EMPTY)
                        } else {
                            setupRecyclerView(it)
                        }
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showScenarioPlaceholder(isVisible = false, Constant.TYPE_EMPTY)
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
                    this@SearchFragment.query = query
                    searchMovies(query)
                    hideSoftKeyboard()
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    override fun searchMovies(query: String) {
        if (checkInternetConnection()) {
            getViewModel().searchMovies(query)
        } else {
            showScenarioPlaceholder(isVisible = true, Constant.TYPE_LOST_CONNECTION)
        }
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

    override fun showScenarioPlaceholder(isVisible: Boolean, type: Int) {
        getViewBinding().rvMovie.isGone = isVisible
        setScenarioComponent(type)
        getViewBinding().layoutScenario.layoutComponentScenario.isVisible = isVisible
    }

    override fun setScenarioComponent(type: Int) {
        when (type) {
            Constant.TYPE_PLACEHOLDER -> {
                setupScenario(
                    R.drawable.ic_search_movies,
                    getString(R.string.text_title_search_movies),
                    getString(R.string.message_search_movies)
                )
            }
            Constant.TYPE_EMPTY -> {
                setupScenario(
                    R.drawable.ic_no_search_results,
                    getString(R.string.text_title_no_search_result),
                    getString(R.string.message_no_search_result)
                )
            }
            Constant.TYPE_LOST_CONNECTION -> {
                setupScenario(
                    R.drawable.ic_no_internet_connection,
                    getString(R.string.text_title_lost_connection),
                    getString(R.string.message_dialog_lost_connection)
                )
                getViewBinding().layoutScenario.btnActionRetry.isVisible = true
                getViewBinding().layoutScenario.btnActionRetry.setOnClickListener {
                    searchMovies(query)
                }
            }
        }
    }

    private fun setupScenario(imgDrawable: Int, title: String, desc: String) {
        getViewBinding().layoutScenario.btnActionRetry.isVisible = false
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