package com.dev.divig.moviereviewsapp.ui.main.adapter

import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.data.model.MovieEntity
import com.dev.divig.moviereviewsapp.databinding.ItemMovieBinding

class MovieAdapter(private val onClickListener: OnClickListener):
    ListAdapter<MovieEntity, MovieAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : androidx.recyclerview.widget.DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }


    inner class ViewHolder(private var binding: ItemMovieBinding):
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root){
        fun bind(movie: MovieEntity){
            binding.tvMovieTitle.text = movie.title
            val imgUrl = movie.basePosterUrl + movie.posterPath
            imgUrl.let {
                val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
                binding.ivMoviePoster.load(imgUri) {
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.ic_broken_image)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(android.view.LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(movie)
        }
        holder.bind(movie)
    }

    class OnClickListener(val clickListener: (movie: MovieEntity) -> Unit){
        fun onClick(movie: MovieEntity) = clickListener(movie)
    }

}