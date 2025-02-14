package com.kurokawa.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
    private var currentMovie :MovieEntity? = null


    /**MAIN---------------------------------------------------------------------------------------*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**INICIALIZACION DE APPLICACION, REPOSITORY Y VIEWMODEL*/
        inicializer()

        /**OBTENER EL ID DE LA MOVIE SELECCIONADA VER LOS CAMBIOS Y MOSTRAR LOS DETALLES DE LA MOVIE*/
        val movieSelected = intent.getParcelableExtra<MovieEntity>("MOVIE")
        if (movieSelected != null){
            observerStateMovies(movieSelected.idMovie)
        }

        /**ESCUCHAR EL BOTON FAVORITO Y ACTUALIZAR EL ESTADO DE LA MOVIE*/
        binding.btnFavorite.setOnClickListener {
            currentMovie?.let { movie ->
                movieViewModel.updateFavoriteMovies(movie)
            }
        }
    }

    /**FUNCIONES----------------------------------------------------------------------------------*/
    /**INICIA APP, REPO Y VIEWMODEL*/
    private fun inicializer() {
        applicacion = application as MyApplication
        repository = MovieDetailRepository(applicacion)
        movieViewModel  = MovieDetailsViewModel(repository)
    }

    /**OBSERVA LA MOVIE Y ACTUALIZA LA VISTA*/
    private fun observerStateMovies(idMovie: Long) {
        movieViewModel.getMovieById(idMovie).observe(this){ updateMovie->
            currentMovie =updateMovie
            showDetailsMovie(updateMovie)
        }
    }


    /**IMPRIME TODOS LOS DETALLES DE LA MOVIE*/
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

}
