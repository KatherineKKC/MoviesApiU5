package com.kurokawa.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kurokawa.data.repository.MovieCategory
import com.kurokawa.data.repository.MovieRepository
import com.kurokawa.data.room.adapter.MoviesListAdapter
import com.kurokawa.databinding.ActivityMoviesListBinding
import com.kurokawa.ui.viewModel.MovieListViewModel
import com.kurokawa.ui.viewModel.MovieViewModelFactory
import com.kurokawa.utils.Resource

class MoviesListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesListBinding
    private lateinit var adapter: MoviesListAdapter
    private lateinit var movieViewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            val repository = MovieRepository(applicationContext)
            val factory = MovieViewModelFactory(repository)
            movieViewModel = ViewModelProvider(this, factory)[MovieListViewModel::class.java]
        } catch (e: Exception) {
            Toast.makeText(this, "Error inicializando ViewModel: ${e.message}", Toast.LENGTH_LONG).show()
        }

        setupRecyclerView()
        observeViewModel()

        movieViewModel.fetchMovies(page = 1, category = MovieCategory.POPULAR)
    }

    private fun setupRecyclerView() {
        adapter = MoviesListAdapter { movieIdSelected ->
            // Lógica para navegación si es necesario
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        movieViewModel.movies.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> binding.progres.isVisible
                is Resource.Success -> {
                    binding.progres.isGone
                    resource.data?.let { movies -> adapter.submitList(movies) }
                }
                is Resource.Error -> {
                    binding.progres.isGone
                    Toast.makeText(this, resource.message ?: "Error desconocido", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
