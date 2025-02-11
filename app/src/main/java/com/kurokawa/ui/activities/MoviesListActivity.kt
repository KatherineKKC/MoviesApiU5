package com.kurokawa.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kurokawa.data.room.adapter.MoviesListAdapter
import com.kurokawa.databinding.ActivityMoviesListBinding
import com.kurokawa.ui.viewModel.MovieListViewModel

class MoviesListActivity : AppCompatActivity() {
    //BINDING
    private lateinit var _binding : ActivityMoviesListBinding
    private val binding: ActivityMoviesListBinding get() = _binding


    //ADAPTER Y RECICLER
    private lateinit var adapter: MoviesListAdapter

    //VIEWMODEL
    private val viewModel: MovieListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMoviesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ADAPTER
        adapter = MoviesListAdapter{ movieIdelected ->
            navigateToDetail(movieIdelected )
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        //Observar los datos del viewModel
        observer()
        //Obtener Peliculas
        viewModel.loadMovies()

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

    private fun navigateToDetail(movieIdSelected: Int) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("MOVIE_ID_SELECTED", movieIdSelected)
        startActivity(intent)

    }

    private fun observer(){
        viewModel.movieListResult.observe(this){ moviesList ->
            if(moviesList.isEmpty()){
                binding.tvMessegeList.visibility = View.VISIBLE
            }else{
                adapter.submitList(moviesList)
            }

        }
    }


    private fun filterMovies(nameMovie: String) {
        val filteredList = viewModel.movieListResult.value?.filter { movie ->
            movie.title.contains(nameMovie, ignoreCase = true)
        }
        adapter.submitList(filteredList)
    }
}