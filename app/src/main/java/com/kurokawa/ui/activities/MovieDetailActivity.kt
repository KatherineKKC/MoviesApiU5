package com.kurokawa.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kurokawa.databinding.ActivityMovieDetailBinding
import com.kurokawa.ui.viewModel.MovieDetailViewModel

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
