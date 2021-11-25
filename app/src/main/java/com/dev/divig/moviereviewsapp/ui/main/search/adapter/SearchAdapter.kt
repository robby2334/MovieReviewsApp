package com.dev.divig.moviereviewsapp.ui.main.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.data.network.model.response.movie.Movie
import com.dev.divig.moviereviewsapp.databinding.ItemMovieBinding
import com.dev.divig.moviereviewsapp.utils.Constant

class SearchAdapter(private val itemClick: (Movie) -> Unit) :
    ListAdapter<Movie, SearchAdapter.ViewHolder>(SearchAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val movie = getItem(position)
        holder.bindView(movie)
    }

    inner class ViewHolder(
        private val binding: ItemMovieBinding,
        private val itemClick: (Movie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Movie) {
            with(item) {
                val imgUrl = Constant.BASE_URL_IMAGE + posterPath
                binding.ivMoviePoster.load(imgUrl) {
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.ic_broken_image)
                }
                binding.tvMovieTitle.text = title
                binding.tvRating.text = voteAverage.toString()
                binding.ratingBar.rating =
                    (voteAverage?.div(2))?.toFloat() ?: Constant.ZERO_FLOAT
                itemView.setOnClickListener {
                    itemClick(this)
                }
            }
        }
    }

    companion object DiffCallback :
        DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }
}