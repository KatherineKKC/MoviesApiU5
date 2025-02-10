package com.kurokawa.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.search.SearchView
import com.kurokawa.data.room.adapter.MoviesListAdapter
import com.kurokawa.databinding.ActivityMoviesListBinding
import com.kurokawa.ui.viewModel.MovieListViewModel

class MoviesListActivity : AppCompatActivity() {
    //BINDING
    private lateinit var _binding : ActivityMoviesListBinding
    private val binding: ActivityMoviesListBinding get() = _binding


    //ADAPTER Y RECICLER
    private lateinit var recycleView: RecyclerView
    private lateinit var adapter: MoviesListAdapter

    //VIEWMODEL
    private val viewModel: MovieListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMoviesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ADAPTER
        adapter = MoviesListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        //Observar los datos del viewModel
        observer()
        //Obtener Peliculas
        getMovies()

        //Filtrar pelis
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterMovies(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterMovies(newText.orEmpty())
                return true
            }
        })

    }

    private fun observer(){
        viewModel.movieListResult.observe(this){ moviesList ->
            adapter.submitList(moviesList)
        }
    }
    private fun getMovies(){
       val moviesList = viewModel.getAllMovies()
    }

    private fun filterMovies(query: String) {
        val filteredList = viewModel.movieListResult.value?.filter { movie ->
            movie.title.contains(query, ignoreCase = true)
        }
        adapter.submitList(filteredList)
    }
}