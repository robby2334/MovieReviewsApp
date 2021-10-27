package com.dev.divig.moviereviewsapp.ui.bottomsheetreview.adapter;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity
import com.dev.divig.moviereviewsapp.databinding.ItemReviewBinding


class BottomReviewDialog (
private val itemClick: (ReviewEntity) -> Unit) : RecyclerView.Adapter<BottomReviewDialog.ViewHolder>()
{


    private var items: MutableList<ReviewEntity> = mutableListOf()

    fun setItems(items: List<ReviewEntity>) {
        clearItems()
        addItems(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<ReviewEntity>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class ViewHolder(
        private val binding: ItemReviewBinding,
        val itemClick: (ReviewEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: ReviewEntity) {
            with(item) {
                binding.tvReviewName.text = item.author
                binding.tvDescReview.text = item.content
                binding.tvDateReview.text = item.createAt
            }
            itemView.setOnClickListener { itemClick(this)
            }
        }
    }

}