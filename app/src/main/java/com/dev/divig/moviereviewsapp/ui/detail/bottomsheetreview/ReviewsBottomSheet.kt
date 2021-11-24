package com.dev.divig.moviereviewsapp.ui.detail.bottomsheetreview

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import coil.load
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseBottomSheetDialogFragment
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.ReviewEntity
import com.dev.divig.moviereviewsapp.databinding.FragmentBottomSheetReviewBinding
import com.dev.divig.moviereviewsapp.ui.detail.bottomsheetreview.adapter.ReviewsBottomSheetAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsBottomSheet(private val movieId: Int, private val isFullScreen: Boolean) :
    BaseBottomSheetDialogFragment<FragmentBottomSheetReviewBinding, ReviewsBottomSheetViewModel>(
        FragmentBottomSheetReviewBinding::inflate
    ), ReviewsBottomSheetContract.View {
    override val viewModelInstance: ReviewsBottomSheetViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        if (isFullScreen) {
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
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun initView() {
        getReviews()
        initScenarioComponent()
    }

    override fun initScenarioComponent() {
        getViewBinding().layoutScenario.ivScenario.load(R.drawable.ic_no_reviews_placeholder)
        getViewBinding().layoutScenario.tvTitle.text = getString(R.string.text_title_no_review)
        getViewBinding().layoutScenario.tvDesc.text = getString(R.string.message_no_review)
    }

    override fun observeViewModel() {
        getViewModel().getReviewsLiveData().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    showEmptyPlaceholder(false)
                    showLoading(true)
                    showContent(false)
                }
                is Resource.Success -> {
                    showLoading(false)
                    if (response.data.isNullOrEmpty()) {
                        showEmptyPlaceholder(true)
                        showContent(false)
                    } else {
                        showEmptyPlaceholder(false)
                        showContent(true)
                        setListData(response.data)
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(true)
                    showError(true, response.message)
                }
            }
        }
    }

    override fun getReviews() {
        getViewModel().getReviewsByMovieId(movieId)
    }

    override fun setListData(reviews: List<ReviewEntity>?) {
        val adapter = ReviewsBottomSheetAdapter()
        adapter.submitList(reviews)
        getViewBinding().rvReview.adapter = adapter
    }


    override fun showEmptyPlaceholder(isVisible: Boolean) {
        getViewBinding().layoutScenario.layoutComponentScenario.isVisible = isVisible
    }

    override fun showContent(isContentVisible: Boolean) {
        super.showContent(isContentVisible)
        getViewBinding().rvReview.isVisible = isContentVisible
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        super.showError(isErrorEnabled, msg)
        if (isErrorEnabled) Toast.makeText(requireActivity(), msg.orEmpty(), Toast.LENGTH_SHORT)
            .show()
    }
}