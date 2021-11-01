package com.dev.divig.moviereviewsapp.ui.detail.bottomsheetreview

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import coil.load
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseBottomSheetDialogFragment
import com.dev.divig.moviereviewsapp.data.local.room.MoviesDatabase
import com.dev.divig.moviereviewsapp.data.local.room.datasource.MoviesDataSourceImpl
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity
import com.dev.divig.moviereviewsapp.databinding.FragmentBottomSheetReviewBinding
import com.dev.divig.moviereviewsapp.ui.detail.bottomsheetreview.adapter.ReviewsBottomSheetAdapter
import com.dev.divig.moviereviewsapp.utils.Constant
import com.dev.divig.moviereviewsapp.utils.StringGenerator
import com.dev.divig.moviereviewsapp.utils.Utils

class ReviewsBottomSheet(
    private val movieId: Int,
    private val onResultCallback: (Boolean) -> Unit
) :
    BaseBottomSheetDialogFragment<FragmentBottomSheetReviewBinding, ReviewsBottomSheetContract.Presenter>(
        FragmentBottomSheetReviewBinding::inflate
    ), ReviewsBottomSheetContract.View {

    override fun initView() {
        getReviews()
        initScenarioComponent()
        setClickListeners()
    }

    override fun initPresenter() {
        context?.let {
            val dataSource =
                MoviesDataSourceImpl(MoviesDatabase.getInstance(it).moviesDao())
            val repository = ReviewsBottomSheetRepository(dataSource)
            setPresenter(ReviewsBottomSheetPresenter(this@ReviewsBottomSheet, repository))
        }
    }

    private fun initScenarioComponent() {
        getViewBinding().layoutScenario.ivScenario.load(R.drawable.ic_no_reviews_placeholder)
        getViewBinding().layoutScenario.tvTitle.text = getString(R.string.text_title_no_review)
        getViewBinding().layoutScenario.tvDesc.text = getString(R.string.message_no_review)
    }


    private fun setClickListeners() {
        getViewBinding().tilReview.setEndIconOnClickListener {
            Utils.hideSoftKeyboard(requireActivity(), it)
            saveReview()
        }
        getViewBinding().etReview.doAfterTextChanged {
            getViewBinding().tilReview.isErrorEnabled = false
        }
    }

    private fun getReviews() {
        getPresenter().getReviewsByMovieId(movieId)
    }

    override fun onLoading() {
        showEmptyPlaceholder(false)
        showLoading(true)
        showContent(false)
    }

    override fun onReadSuccess(response: List<ReviewEntity>) {
        showEmptyPlaceholder(false)
        showLoading(false)
        showContent(true)
        setListData(response)
    }

    override fun onInsertSuccess() {
        showLoading(false)
        showContent(true)
        clearForm()
        getReviews()
    }

    override fun onDeleteSuccess() {
        showLoading(false)
        showContent(true)
        getReviews()
    }

    override fun onDataFailed(response: Pair<Int, String?>) {
        showLoading(false)
        showContent(true)
        val message = when (response.first) {
            Constant.ACTION_INSERT -> getString(R.string.message_error_insert)
            Constant.ACTION_DELETE -> getString(R.string.message_error_delete)
            else -> response.second.orEmpty()
        }
        showError(true, message)
    }

    override fun onDataEmpty() {
        showEmptyPlaceholder(true)
        showLoading(false)
        showContent(false)
    }

    private fun setListData(reviews: List<ReviewEntity>) {
        val adapter = ReviewsBottomSheetAdapter { item ->
            Utils.showAlertDialog(
                requireActivity(),
                getString(R.string.text_title_delete_review),
                getString(R.string.message_delete_review)
            ) { isClickPositiveButton ->
                if (isClickPositiveButton) deleteReview(item)
            }
        }
        adapter.submitList(reviews)
        getViewBinding().rvReview.adapter = adapter
    }

    private fun saveReview() {
        if (validateForm()) {
            val review = ReviewEntity(
                StringGenerator.generateId(),
                movieId,
                Constant.USERNAME,
                Constant.USERNAME,
                getViewBinding().etReview.text.toString().trim(),
                Utils.getDate()
            )
            getPresenter().insertReview(review)
        }
    }

    private fun validateForm(): Boolean {
        val content = getViewBinding().etReview.text.toString().trim()
        return if (content.isEmpty()) {
            getViewBinding().tilReview.error = getString(R.string.message_error_field_request)
            false
        } else {
            getViewBinding().tilReview.isErrorEnabled = false
            true
        }
    }

    private fun clearForm() {
        getViewBinding().etReview.text = null
    }

    private fun deleteReview(review: ReviewEntity) {
        getPresenter().deleteReview(review)
    }

    private fun showEmptyPlaceholder(isVisible: Boolean) {
        getViewBinding().layoutScenario.layoutComponentScenario.isVisible = isVisible
    }

    override fun showContent(isContentVisible: Boolean) {
        super.showContent(isContentVisible)
        getViewBinding().rvReview.isVisible = isContentVisible
    }

    override fun showLoading(isLoading: Boolean) {
        super.showLoading(isLoading)
        getViewBinding().sflItemPlaceholder.isVisible = isLoading
        if (isLoading) {
            getViewBinding().sflItemPlaceholder.startShimmer()
        } else {
            getViewBinding().sflItemPlaceholder.stopShimmer()
        }
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        super.showError(isErrorEnabled, msg)
        if (isErrorEnabled) Toast.makeText(requireActivity(), msg.orEmpty(), Toast.LENGTH_SHORT)
            .show()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                val bottomSheet =
                    findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                bottomSheet.setBackgroundResource(android.R.color.transparent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onResultCallback(true)
    }
}