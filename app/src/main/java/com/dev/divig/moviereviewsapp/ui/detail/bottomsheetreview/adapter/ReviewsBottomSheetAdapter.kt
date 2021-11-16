package com.dev.divig.moviereviewsapp.ui.detail.bottomsheetreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dev.divig.moviereviewsapp.data.local.model.ReviewEntity
import com.dev.divig.moviereviewsapp.databinding.ItemReviewBinding
import com.dev.divig.moviereviewsapp.utils.Utils

class ReviewsBottomSheetAdapter() :
    ListAdapter<ReviewEntity, ReviewsBottomSheetAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: ReviewEntity) {
            with(item) {
                binding.tvReviewName.text = author
                binding.tvDescReview.text = content
                binding.tvDateReview.text = Utils.dateFormatter(createAt)
            }
        }
    }

    companion object DiffCallback :
        androidx.recyclerview.widget.DiffUtil.ItemCallback<ReviewEntity>() {
        override fun areItemsTheSame(oldItem: ReviewEntity, newItem: ReviewEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ReviewEntity, newItem: ReviewEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }
}