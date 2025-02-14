package com.kurokawa.view

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
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

        /**OBTENER EL ID DE LA MOVIE SELECCIONADA L*/
         val movieSelected = intent.getParcelableExtra<MovieEntity>("MOVIE")

        showDetailsMovie(movieSelected)

        /**SELECCION DE MOVIE FAVORITA*/
        var isFavorite :Boolean = true
        binding.btnFavorite.setOnClickListener{
           isFavorite =!isFavorite
            if (movieSelected != null) {
                isFavorite(isFavorite, movieSelected)
            }
        }


    }

    @SuppressLint("SetTextI18n")
    private fun showDetailsMovie(movie: MovieEntity?) {
        if (movie != null){
            binding.tvTitle.text = movie.title
            binding.tvOriginal.text = movie.originalTitle
            binding.tvOverview.text = movie.overview
            binding.tvRelease.text = movie.releaseDate
            binding.tvVote.text = movie.voteAverage.toString()
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(binding.imgPoster)
        }else{
            Toast.makeText(this, "No se pudo obtener el detalle de la pelicula ", Toast.LENGTH_SHORT).show()
        }

    }


    private fun isFavorite(isFavorite :Boolean, movieSelected: MovieEntity){
        if (isFavorite ){
            binding.btnFavorite.imageTintList = ColorStateList.valueOf(Color.parseColor("#FFD700"))

                movieViewModel.movieIsFavorite(isFavorite, movieSelected)
        }else{
            binding.btnFavorite.imageTintList = ColorStateList.valueOf(Color.parseColor("#21005C"))
        }
    }


}
