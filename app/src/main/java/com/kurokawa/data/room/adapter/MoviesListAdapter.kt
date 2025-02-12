package com.kurokawa.data.room.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kurokawa.R
import com.kurokawa.data.room.entities.Movies
import com.kurokawa.databinding.ItemMoviesBinding
import com.kurokawa.data.room.difu.MovieDifu

class MoviesListAdapter(
    private val onItemClick: (Int) -> Unit  // Callback para clics
) : RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>() {

    private val movies = mutableListOf<Movies>()

    // Actualizar la lista de películas usando DiffUtil
    fun submitList(newMovies: List<Movies>) {
        val diffResult = DiffUtil.calculateDiff(MovieDifu(movies, newMovies))
        movies.clear()
        movies.addAll(newMovies)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    inner class MovieViewHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movies) {
            binding.tvTitleMovieList.text = movie.title

            // Cargar imagen del póster con Glide
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .placeholder(R.drawable.ic_launcher_foreground)  // Placeholder mientras carga
                .error(R.drawable.ic_launcher_background)         // Imagen por defecto si hay error
                .into(binding.imgMovieList)

            // Manejo del clic en el ítem
            binding.root.setOnClickListener {
                onItemClick(movie.idMovie)
            }
        }
    }
}
