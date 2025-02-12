package com.kurokawa.data.room.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kurokawa.data.room.difu.MovieDifu
import com.kurokawa.data.room.entities.MovieEntity
import com.kurokawa.databinding.ItemMoviesBinding
import com.kurokawa.model.MovieModel

class MoviesListAdapter(
    var listMovies: MutableList<MovieEntity>,
    private val onClick: (MovieEntity) -> Unit) : RecyclerView.Adapter<MovieListViewHolder>() {

    private val movies = mutableListOf<MovieEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie, onClick)
    }

    override fun getItemCount(): Int = movies.size

    //Actualiza la lista de peliculas
    fun submitList(newMovies: List<MovieEntity>) {
        val diffResult = DiffUtil.calculateDiff(MovieDifu(movies, newMovies))
        movies.clear()
        movies.addAll(newMovies)
        diffResult.dispatchUpdatesTo(this)
    }

    fun updateData(movies: List<MovieModel>?) {

    }


}


