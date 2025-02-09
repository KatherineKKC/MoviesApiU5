package com.kurokawa.ui.detail

import android.R
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kurokawa.data.room.model.Movies
import com.kurokawa.databinding.ActivityMovieDetailBinding
import com.kurokawa.ui.movieList.adapter.MoviesAdapter

class MovieDetailActivity : AppCompatActivity() {
    // BINDING
    private lateinit var _binding: ActivityMovieDetailBinding
    private val binding: ActivityMovieDetailBinding get() = _binding



    // VIEWMODEL
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // RECIBIR DATOS DE LA PELÍCULA
        var idMovieSelected = intent.getIntExtra("idMovie", -1)



    }



}
