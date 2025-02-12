package com.kurokawa.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kurokawa.application.MyApplication
import com.kurokawa.data.remote.retrofit.RetrofitClient
import com.kurokawa.data.repository.MovieListRepository
import com.kurokawa.data.room.adapter.MoviesListAdapter
import com.kurokawa.data.room.entities.MovieEntity
import com.kurokawa.databinding.ActivityMoviesListBinding
import com.kurokawa.viewModel.MovieListViewModel

class MoviesListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesListBinding
    private lateinit var movieViewModel: MovieListViewModel
    private lateinit var adapter : MoviesListAdapter
    private lateinit var applicacion: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val listMovies = mutableListOf<MovieEntity>()
         adapter = MoviesListAdapter(listMovies){ movies ->
            onItemSelected(movies)
        }
        binding.recyclerView.adapter = adapter

        //DAO
        applicacion = application as MyApplication

        //API
        val apiService = RetrofitClient.apiService
        val repository = MovieListRepository(apiService,applicacion)
        movieViewModel = MovieListViewModel(repository)



    }

    private fun loadMoviesApi(){
        movieViewModel.loadPopularMovies(1)
        movieViewModel.popularMovie.observe(this){ popular ->
        }

        movieViewModel.loadTopRateMovies(1)
        movieViewModel.topRatedMovieRoom.observe(this){}

        movieViewModel.loadNowPlayingMovies(1)
        movieViewModel.nowPlayingMovie.observe(this){}

        movieViewModel.loadUpcommingMovies(1)
        movieViewModel.upcomingMovie.observe(this){}
    }

    private fun loadAllMoviesRoom(){
        movieViewModel.getAllMoviesRoom.observe(this){moviesRoom ->
            if (moviesRoom != null){
                this.adapter.submitList(moviesRoom)
            }else{
                Log.e("ERROR ACTIVITY", "No hay películas en la base de datos.")
            }
        }


    }

    private fun onItemSelected(movies: MovieEntity) {
        if(movies != null){
            navigateToMovieDetail(movies)
        }else{
            Log.e("ERROR SELECTED MOVIE", "Error desde MovieListActivy al seleccionar la pelicula")
        }

    }

    private fun navigateToMovieDetail(movieSelected: MovieEntity) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("MOVIE", movieSelected)
        startActivity(intent)

    }


}
