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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val movie = listMovies[position]
        holder.bind(movie, onClick)
    }

    override fun getItemCount(): Int = listMovies.size

    //Actualiza la lista de peliculas
    fun submitList(newlistMovies: List<MovieEntity>) {
        val diffResult = DiffUtil.calculateDiff(MovieDifu(listMovies, newlistMovies))
        listMovies.clear()
        listMovies.addAll(newlistMovies)
        diffResult.dispatchUpdatesTo(this)
    }

    fun updateMovie(updatedMovie: MovieEntity) {
        val index = listMovies.indexOfFirst { it.idMovie == updatedMovie.idMovie }
        if (index != -1) {
            listMovies[index] = updatedMovie
            notifyItemChanged(index)
        }
    }



}


