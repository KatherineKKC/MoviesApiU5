package com.kurokawa.ui.movieList

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.kurokawa.databinding.ActivityMoviesListBinding
import com.kurokawa.repository.MovieRepository
import com.kurokawa.ui.movieList.adapter.MoviesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesListActivity : AppCompatActivity() {
    //BINDING
    private lateinit var _binding : ActivityMoviesListBinding
    private val binding: ActivityMoviesListBinding get() = _binding

    //ADAPTER Y RECICLER
    private lateinit var recycleView: RecyclerView
    private lateinit var moviAdapter: MoviesAdapter


    //VIEWMODEL
    private val viewModel: MovieListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMoviesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //

    }

    private fun getMovies(){
        lifecycleScope.launch(Dispatchers.IO){


        }
    }
}