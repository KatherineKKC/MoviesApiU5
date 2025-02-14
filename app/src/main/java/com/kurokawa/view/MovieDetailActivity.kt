package com.kurokawa.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kurokawa.R
import com.kurokawa.application.MyApplication
import com.kurokawa.data.repository.MovieDetailRepository
import com.kurokawa.data.room.entities.MovieEntity
import com.kurokawa.databinding.ActivityMovieDetailBinding
import com.kurokawa.viewModel.MovieDetailsViewModel

class MovieDetailActivity : AppCompatActivity() {
    /**VARIABLES DECLARADAS-----------------------------------------------------------------------*/
    private lateinit var _binding: ActivityMovieDetailBinding
    private val binding: ActivityMovieDetailBinding get() = _binding
    private lateinit var applicacion :MyApplication
    private lateinit var movieViewModel :MovieDetailsViewModel
    private lateinit var repository: MovieDetailRepository


    /**MAIN---------------------------------------------------------------------------------------*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /**INICIALIZACION DE APPLICACION, REPOSITORY Y VIEWMODEL*/
         applicacion = application as MyApplication
         repository = MovieDetailRepository(applicacion)
         movieViewModel  = MovieDetailsViewModel(repository)

        /**OBTENER EL ID DE LA MOVIE SELECCIONADA Y MOSTRAR LOS DETALLES DE LA MOVIE*/
         val movieSelected = intent.getParcelableExtra<MovieEntity>("MOVIE")
        /**SELECCION DE MOVIE FAVORITA*/

        // Listener para marcar como favorita
        binding.btnFavorite.setOnClickListener {
            movieSelected?.let {
                val newFavoriteState = !it.isFavoriteMovie
                Log.e("MOVIE-DETAIL-ACTIVITY", "Nuevo estado de favorito: $newFavoriteState")
                movieViewModel.updateFavoriteMovies(it.copy(isFavoriteMovie = newFavoriteState))
            }
        }

        if (movieSelected != null) {
            Log.e("MOVIE-DETAIL-ACTIVITY", "Película recibida: $movieSelected")

            movieViewModel.getMovieById(movieSelected.idMovie).observe(this) { movie ->
                movie?.let { movie ->
                    Log.e("MOVIE-DETAIL-ACTIVITY", "Datos observados de Room: $movie")
                    showDetailsMovie(movie)
                }
            }
        } else {
            Log.e("MOVIE-DETAIL-ACTIVITY", "No se recibió película en el Intent")
            Toast.makeText(this, "Error al cargar detalles de la película", Toast.LENGTH_SHORT).show()
        }

    }


    @SuppressLint("SetTextI18n")
    private fun showDetailsMovie(movie: MovieEntity) {
        binding.tvTitle.text = movie.title
        binding.tvOriginal.text = movie.originalTitle
        binding.tvOverview.text = movie.overview
        binding.tvRelease.text = movie.releaseDate
        binding.tvVote.text = movie.voteAverage.toString()

        binding.btnFavorite.isChecked = movie.isFavoriteMovie
        Log.e("MOVIE-DETAIL-ACTIVITY", "Actualizando UI con favorito: ${movie.isFavoriteMovie}")

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .into(binding.imgPoster)
    }




    private fun movieFavorite( movieSelected: MovieEntity){
            /** ENVIA LA MOVIE  */
                movieViewModel.updateFavoriteMovies(movieSelected)
                Toast.makeText(this, "Lista de Favoritas actualizada", Toast.LENGTH_SHORT).show()
    }

}
