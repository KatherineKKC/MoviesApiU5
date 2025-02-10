package com.kurokawa.data.room.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kurokawa.R
import com.kurokawa.data.room.entities.Movies

class MoviesListAdapter : ListAdapter<Movies, MoviesListAdapter.MoviesListHolder>(DiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListAdapter.MoviesListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return MoviesListHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesListHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun submitList(moviesList: Unit) {

    }


    inner class MoviesListHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tv_title_movie_list)
        private val poster: ImageView = itemView.findViewById(R.id.img_movie_list)

        fun bind(movie: Movies) {
            title.text = movie.title
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .into(poster)

        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Movies>() {
        override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean = oldItem.idMovie == newItem.idMovie
        override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean = oldItem == newItem
    }

}