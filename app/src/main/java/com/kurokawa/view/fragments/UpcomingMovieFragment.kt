package com.kurokawa.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.kurokawa.R
import com.kurokawa.data.room.adapter.MoviesListAdapter
import com.kurokawa.data.room.entities.MovieEntity
import com.kurokawa.databinding.FragmentPopularMovieBinding
import com.kurokawa.databinding.FragmentUpcomingMovieBinding
import com.kurokawa.view.activities.MovieDetailActivity
import com.kurokawa.viewModel.MovieListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpcomingMovieFragment : Fragment() {

    private lateinit var _binding : FragmentUpcomingMovieBinding
    private val binding: FragmentUpcomingMovieBinding get() = _binding

    private lateinit var adapter: MoviesListAdapter
    private val viewModel : MovieListViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):  View? {

        _binding = FragmentUpcomingMovieBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecicler()
        getAllUpcomingMovies()
        observerFilter()
    }

    private fun setupRecicler(){
        adapter = MoviesListAdapter(mutableListOf()){ movieDetail ->
            navigateToDetail(movieDetail)
        }
        binding.recyclerViewUpcoming.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewUpcoming.adapter = adapter
    }

    private fun observerFilter(){
        viewModel.filteredMovies.observe(viewLifecycleOwner) { filteredList ->
            Log.e("ALL-MOVIES-FRAGMENT", "Actualizando RecyclerView con ${filteredList.size} películas")
            val uniqueList = filteredList.distinctBy { it.idMovie }
            adapter.submitList(uniqueList)
        }

    }

    private fun navigateToDetail(movieDetail: MovieEntity) {
        val intent = Intent(requireContext(), MovieDetailActivity::class.java)
        intent.putExtra("MOVIE", movieDetail)
        startActivity(intent)
    }

    private fun getAllUpcomingMovies(){
        viewModel.getMovieByCategory("Upcoming").observe(viewLifecycleOwner){upcommingList ->
            val uniqueList = upcommingList.distinctBy { it.idMovie }
            adapter.submitList(uniqueList)
        }

    }

}