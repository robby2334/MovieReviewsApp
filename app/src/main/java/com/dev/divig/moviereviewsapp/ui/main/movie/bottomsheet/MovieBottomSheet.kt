package com.dev.divig.moviereviewsapp.ui.main.movie.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import coil.load
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseBottomSheetDialogFragment
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.MovieEntity
import com.dev.divig.moviereviewsapp.databinding.FragmentBottomSheetMoviesBinding
import com.dev.divig.moviereviewsapp.ui.detail.DetailActivity
import com.dev.divig.moviereviewsapp.ui.main.adapter.MoviesAdapter
import com.dev.divig.moviereviewsapp.utils.Constant
import com.dev.divig.moviereviewsapp.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieBottomSheet(private val genre: String) :
    BaseBottomSheetDialogFragment<FragmentBottomSheetMoviesBinding, MovieBottomSheetViewModel>(
        FragmentBottomSheetMoviesBinding::inflate
    ), MovieBottomSheetContract.View {

    override val viewModelInstance: MovieBottomSheetViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet)
            parentLayout?.let { view ->
                val behaviour = BottomSheetBehavior.from(view)
                setupFullHeight(view)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun initView() {
        getViewBinding().tvGenreTitle.text = genre
        getMovies()
    }

    override fun initScenarioComponent() {
        getViewBinding().layoutScenario.ivScenario.load(R.drawable.ic_no_reviews_placeholder)
        getViewBinding().layoutScenario.tvTitle.text =
            getString(R.string.text_title_no_review)
        getViewBinding().layoutScenario.tvDesc.text =
            getString(R.string.message_no_review)
    }

    override fun observeViewModel() {
        getViewModel().getMoviesLiveData().observe(viewLifecycleOwner) { response ->
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

    override fun getMovies() {
        getViewModel().getMovies()
    }

    override fun setupRecyclerView(movies: List<MovieEntity>) {
        val listMovies = if (genre == Constant.NOW_PLAYING_MOVIES) {
            movies.filter {
                Utils.dateToMillis(it.releaseDate) <= Utils.dateToMillis(Utils.getDate())
            }.sortedByDescending { Utils.dateToMillis(it.releaseDate) }
        } else {
            movies.filter { item -> Utils.splitGenre(item.genres).find { it == genre } == genre }
        }

        val moviesAdapter = MoviesAdapter {
            navigateToDetail(it)
        }
        moviesAdapter.submitList(
            listMovies
        )
        getViewBinding().rvMovie.apply {
            adapter = moviesAdapter
        }
    }

    private fun navigateToDetail(movie: MovieEntity) {
        DetailActivity.startActivity(requireContext(), movie.id, false)
    }

    override fun showEmptyPlaceholder(isVisible: Boolean) {
        getViewBinding().layoutScenario.layoutComponentScenario.isVisible = isVisible
        getViewBinding().rvMovie.isGone = isVisible
    }
}