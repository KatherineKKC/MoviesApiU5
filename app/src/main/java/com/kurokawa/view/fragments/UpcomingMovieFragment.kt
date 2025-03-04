package com.kurokawa.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.kurokawa.data.dataStore.adapter.MoviesListAdapter
import com.kurokawa.data.dataStore.entities.MovieEntity
import com.kurokawa.databinding.FragmentUpcomingMovieBinding
import com.kurokawa.view.activities.MovieDetailActivity
import com.kurokawa.viewModel.MovieListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UpcomingMovieFragment : Fragment(), FragmentMetodos {
    /**VARIABLES DECLARADAS-----------------------------------------------------------------------*/
    private lateinit var _binding: FragmentUpcomingMovieBinding
    private val binding: FragmentUpcomingMovieBinding get() = _binding
    private lateinit var adapter: MoviesListAdapter
    private val viewModel: MovieListViewModel by sharedViewModel()

    /**VISTA--------------------------------------------------------------------------------------*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUpcomingMovieBinding.inflate(inflater)
        return binding.root
    }

    /**MAIN---------------------------------------------------------------------------------------*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        getMovies()
        observerFilter()
    }

    /**FUNCIONES IMPLEMENTADAS--------------------------------------------------------------------*/
    override fun setupRecycler() {
        adapter = MoviesListAdapter(mutableListOf()) { movieDetail ->
            navigateToMovieDetail(movieDetail)
        }
        binding.recyclerViewUpcoming.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewUpcoming.adapter = adapter
    }

    override fun observerFilter() {
        lifecycleScope.launch {
            viewModel.filteredMovies.collectLatest { filteredList ->
                Log.e(
                    "ALL-MOVIES-FRAGMENT",
                    "Actualizando RecyclerView con ${filteredList.size} películas"
                )

                val uniqueList = filteredList.distinctBy { it.idMovie } // 🔹 Evita duplicados

                adapter.submitList(uniqueList) // 🔹 Se ejecuta en el hilo principal
            }
        }
    }


    override fun getMovies() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getMovieByCategory("Upcoming").collect { nowPlayingMoveList ->
                val uniqueList = nowPlayingMoveList.distinctBy { it.idMovie }
                withContext(Dispatchers.Main) {
                    adapter.submitList(uniqueList)
                    viewModel.filterFavorites("")

                }
            }
        }
    }


    override fun navigateToMovieDetail(movieDetail: MovieEntity) {
        val intent = Intent(requireContext(), MovieDetailActivity::class.java)
        intent.putExtra("MOVIE", movieDetail)
        startActivity(intent)
    }

}