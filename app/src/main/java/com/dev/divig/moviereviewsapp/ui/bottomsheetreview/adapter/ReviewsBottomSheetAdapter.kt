package com.dev.divig.moviereviewsapp.ui.bottomsheetreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity
import com.dev.divig.moviereviewsapp.databinding.ItemReviewBinding
import com.dev.divig.moviereviewsapp.utils.Constant
import com.dev.divig.moviereviewsapp.utils.Utils

class ReviewsBottomSheetAdapter(
    private val itemClick: (ReviewEntity) -> Unit
) : ListAdapter<ReviewEntity, ReviewsBottomSheetAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemReviewBinding,
        val itemClick: (ReviewEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: ReviewEntity) {
            with(item) {
                binding.ivBtnDeleteReview.isVisible = item.username.equals(Constant.USERNAME)
                binding.tvReviewName.text = item.author
                binding.tvDescReview.text = item.content
                binding.tvDateReview.text = Utils.dateFormatter(item.createAt)
                binding.ivBtnDeleteReview.setOnClickListener {
                    itemClick(this)
                }
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