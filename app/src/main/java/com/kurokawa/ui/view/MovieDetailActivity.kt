package com.kurokawa.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kurokawa.databinding.ActivityMovieDetailBinding
import com.kurokawa.ui.viewModel.MovieDetailsViewModel

class MovieDetailActivity : AppCompatActivity() {
    // BINDING
    private lateinit var _binding: ActivityMovieDetailBinding
    private val binding: ActivityMovieDetailBinding get() = _binding




    // VIEWMODEL
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        var movieId = intent.getIntExtra("MOVIE_ID_SELECTED", -1).toInt()
        getDataMovie(movieId)


    }

    private fun obrserver(){
       viewModel.movieDetailResponse.observe(this){ movieDetail ->

           //Actualiza la UI con los datos de la pelicula
           binding.tvTitle.text = movieDetail.title
           binding.tvOriginalTitle.text = movieDetail.originalTitle
           binding.tvOverview.text = movieDetail.overview
           binding.tvReleaseDate.text = movieDetail.releaseDate
           binding.tvVoteAverage.text = "⭐ ${movieDetail.voteAverage}"

           //Carga la imagen del poster
           Glide.with(this)
               .load("https://image.tmdb.org/t/p/w500${movieDetail.posterPath}")
               .into(binding.imgPosterPath)

           // Actualiza el icono de favoritos
           updateFavoriteIcon(movieDetail.isFavoriteMovie)


       }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean){
        val icon = if (isFavorite) {
            android.R.drawable.btn_star_big_on
        } else {
            android.R.drawable.btn_star_big_off
        }
        binding.btnStarFavorite.setImageResource(icon)
    }

    fun getDataMovie(movieId:Int){
        if (movieId != -1 ){
            val movie = viewModel.getMovieDetails(movieId)
        }else{
            Toast.makeText(this,"El id de la movie no es correcto ", Toast.LENGTH_SHORT).show()
        }




    }



}
