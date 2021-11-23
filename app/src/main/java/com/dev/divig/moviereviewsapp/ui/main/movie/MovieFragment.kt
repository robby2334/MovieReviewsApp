package com.dev.divig.moviereviewsapp.ui.main.movie

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseFragment
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.MovieEntity
import com.dev.divig.moviereviewsapp.databinding.FragmentMovieBinding
import com.dev.divig.moviereviewsapp.ui.detail.DetailActivity
import com.dev.divig.moviereviewsapp.ui.main.movie.adapter.ParentItemAdapter
import com.dev.divig.moviereviewsapp.ui.main.movie.bottomsheet.MovieBottomSheet
import com.dev.divig.moviereviewsapp.ui.main.movie.model.ParentEntity
import com.dev.divig.moviereviewsapp.utils.SpacesItemDecoration
import com.dev.divig.moviereviewsapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment :
    BaseFragment<FragmentMovieBinding, MovieViewModel>(FragmentMovieBinding::inflate),
    MovieContract.View {

    override val viewModelInstance: MovieViewModel by viewModels()

    override fun initView() {
        initSwipeRefresh()
        getMovies(false)
    }

    override fun observeViewModel() {
        getViewModel().getMoviesLiveData().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    response.data?.let {
                        if (it.isEmpty()) {
                            showError(true, getString(R.string.message_empty_movies))
                        } else {
                            setupRecyclerView(it)
                        }
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showError(true, response.message)
                }
            }
        }
    }

    override fun getMovies(update: Boolean) {
        if (checkInternetConnection()) {
            getViewModel().getMovies(update)
        } else {
            Utils.noInternetDialog(
                requireActivity(),
                getString(R.string.text_placeholder_close),
                {
                    getMovies(update)
                    it?.dismiss()
                },
                {
                    it?.dismiss()
                }
            )
        }
    }

    override fun setupRecyclerView(movies: List<MovieEntity>) {
        val parentAdapter = ParentItemAdapter(parentItemList(movies),
            {
                showBottomSheetMovies(it.parentItemTitle)
            },
            {
                navigateToDetail(it)
            })

        with(getViewBinding().rvMovie) {
            adapter = parentAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                SpacesItemDecoration(
                    Utils.dpToPixels(
                        requireContext(),
                        0
                    )
                )
            )
        }
    }

    private fun parentItemList(movies: List<MovieEntity>): List<ParentEntity> {
        val itemList: MutableList<ParentEntity> = ArrayList()
        itemList.add(
            ParentEntity(
                getString(R.string.text_title_image_slider),
                movies
            )
        )
        itemList.add(
            ParentEntity(
                getString(R.string.text_title_now_playing_movies),
                movies.filter {
                    Utils.dateToMillis(it.releaseDate) <= Utils.dateToMillis(Utils.getDate())
                }.sortedByDescending { Utils.dateToMillis(it.releaseDate) }
            )
        )

        val listGenrePlaceholder: MutableList<String> =
            resources.getStringArray(R.array.genre_name_list).toMutableList()

        listGenrePlaceholder.forEach { value ->
            val item = ParentEntity(
                value,
                movies.filter { item ->
                    Utils.splitGenre(item.genres).find { it == value } == value
                }
            )
            if (item.childItemList.isNotEmpty() && item.childItemList.size >= 4) itemList.add(item)
        }
        return itemList
    }

    private fun navigateToDetail(movie: MovieEntity) {
        DetailActivity.startActivity(requireContext(), movie.id, false)
    }

    private fun showBottomSheetMovies(genre: String) {
        MovieBottomSheet(genre).show(childFragmentManager, null)
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
        if (msg.isNullOrEmpty().not()) {
            showSnackBarError(msg)
        }
    }

    override fun initSwipeRefresh() {
        getViewBinding().srlContent.setOnRefreshListener {
            getViewBinding().srlContent.isRefreshing = false
            getMovies(true)
        }
    }
}