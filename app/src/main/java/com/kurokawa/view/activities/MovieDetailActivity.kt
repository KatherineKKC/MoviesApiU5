package com.kurokawa.view.activities

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kurokawa.R
import com.kurokawa.data.room.entities.MovieEntity
import com.kurokawa.databinding.ActivityMovieDetailBinding
import com.kurokawa.viewModel.MovieDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailActivity : AppCompatActivity() {
    /**VARIABLES DECLARADAS-----------------------------------------------------------------------*/
    private lateinit var _binding: ActivityMovieDetailBinding
    private val binding: ActivityMovieDetailBinding get() = _binding
    private val movieViewModel: MovieDetailsViewModel by viewModel()
    private var currentMovie: MovieEntity? = null


    /**MAIN---------------------------------------------------------------------------------------*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //OBTENER EL ID DE LA MOVIE SELECCIONADA VER LOS CAMBIOS Y MOSTRAR LOS DETALLES DE LA MOVIE
        getMovieIntent()

        //ESCUCHAR EL BOTON FAVORITO Y ACTUALIZAR EL ESTADO DE LA MOVIE
        binding.btnFavorite.setOnClickListener {
            currentMovie?.let { movie ->
                movieViewModel.updateFavoriteMovies(movie)
            }
        }

        //Boton para regresar a la vista anterior
        binding.btnBack.setOnClickListener {
            navigateToLastView()
        }
    }


    /**FUNCIONES----------------------------------------------------------------------------------*/
    private fun getMovieIntent() {
        val movieSelected: MovieEntity? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("MOVIE", MovieEntity::class.java)
            } else {
                @Suppress("DEPRECATION") // Suprime la advertencia en versiones antiguas
                intent.getParcelableExtra("MOVIE")
            }

        if (movieSelected != null) {
            observerStateMovies(movieSelected.idMovie)
        }

    }

    //Observa que la movie haya sido seleccionada como Favorita o no y la actualiza
    private fun observerStateMovies(idMovie: Long) {
        movieViewModel.getMovieById(idMovie).observe(this) { updateMovie ->
            currentMovie = updateMovie
            showDetailsMovie(updateMovie)
        }
    }


    //Muestra todos los detalles de la Movie recibida
    @SuppressLint("SetTextI18n")
    private fun showDetailsMovie(movie: MovieEntity) {
        binding.tvTitle.text = movie.title
        binding.tvOriginal.text = movie.originalTitle
        binding.tvOverview.text = movie.overview
        binding.tvRelease.text = movie.releaseDate
        binding.tvVote.text = movie.voteAverage.toString()
        binding.btnFavorite.isChecked = movie.isFavoriteMovie
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .into(binding.imgPoster)

        Log.e("MOVIE-DETAIL-ACTIVITY", "Actualizando UI con favorito: ${movie.isFavoriteMovie}")
    }


    //Navega a la vista anterior
    private fun navigateToLastView() {
        finish() // Cierra la actividad actual y vuelve a la anterior sin recargarla
    }


}
