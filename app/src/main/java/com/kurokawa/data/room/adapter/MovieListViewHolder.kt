package com.kurokawa.data.room.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kurokawa.R
import com.kurokawa.data.room.entities.MovieEntity
import com.kurokawa.databinding.ItemMoviesBinding

class MovieListViewHolder(view: ItemMoviesBinding) : RecyclerView.ViewHolder(view.root) {
    private val binding = ItemMoviesBinding.bind(view.root)

        fun bind(movie: MovieEntity, onClickListener: (MovieEntity) -> Unit) {
            binding.tvTitleMovieList.text = movie.title

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(binding.imgMovieList)

            binding.root.setOnClickListener { onClickListener(movie) }
        }
}