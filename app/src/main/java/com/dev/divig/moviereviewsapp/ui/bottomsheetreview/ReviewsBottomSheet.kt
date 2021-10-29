package com.dev.divig.moviereviewsapp.ui.bottomsheetreview

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.divig.moviereviewsapp.databinding.FragmentBottomSheetReviewBinding
import com.dev.divig.moviereviewsapp.ui.bottomsheetreview.adapter.ReviewsBottomSheetAdapter
import com.dev.divig.moviereviewsapp.utils.DataDummy
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReviewsBottomSheet() : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetReviewBinding
    private lateinit var buttonAdapter: ReviewsBottomSheetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetReviewBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonAdapter = ReviewsBottomSheetAdapter {}
        buttonAdapter.submitList(DataDummy.getReviews())
        binding.tilReview.setEndIconOnClickListener {
            Toast.makeText(context, "Send Review", Toast.LENGTH_SHORT).show()
        }

        with(binding.rvReview) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = buttonAdapter
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(requireActivity(), "Destroy", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            // window?.setDimAmount(0.2f) // Set dim amount here
            setOnShowListener {
                val bottomSheet =
                    findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                bottomSheet.setBackgroundResource(android.R.color.transparent)
            }
        }
    }

}