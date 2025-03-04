package com.kurokawa.view.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.kurokawa.data.sharedPreferences.adapter.MoviesListAdapter
import com.kurokawa.data.sharedPreferences.entities.MovieEntity
import com.kurokawa.databinding.FragmentAllMoviesBinding
import com.kurokawa.view.activities.MovieDetailActivity
import com.kurokawa.viewModel.MovieListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AllMoviesFragment : Fragment(), FragmentMetodos {
    private lateinit var _binding: FragmentAllMoviesBinding
    private val binding: FragmentAllMoviesBinding get() = _binding

    private lateinit var adapter: MoviesListAdapter
    private val allViewModel: MovieListViewModel by sharedViewModel() // activityViewModels() si fuera activity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllMoviesBinding.inflate(inflater)
        return binding.root
    }

    /**LOGICA*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        getMovies()
        observerFilter()
    }


    /**FUNCIONES----------------------------------------------------------------------------------*/
    override fun setupRecycler() {
        adapter = MoviesListAdapter(mutableListOf()) { movie ->
            navigateToMovieDetail(movie)
        }
        binding.recyclerViewAll.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewAll.adapter = adapter
    }

    override fun observerFilter() {
        allViewModel.filteredMovies.observe(viewLifecycleOwner) { listMovies ->
            adapter.submitList(listMovies)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun getMovies() {
        allViewModel.allMovies.observe(viewLifecycleOwner) { movies ->
            allViewModel.filterMovies("")
            adapter.submitList(movies)
        }

    }


    override fun navigateToMovieDetail(movieDetail: MovieEntity) {
        val intent = Intent(requireContext(), MovieDetailActivity::class.java)
        intent.putExtra("MOVIE", movieDetail)
        startActivity(intent)
    }
}
