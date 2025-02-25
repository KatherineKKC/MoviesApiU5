package com.kurokawa.view.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.kurokawa.data.sharedPreferences.adapter.MoviesListAdapter
import com.kurokawa.data.sharedPreferences.entities.MovieEntity
import com.kurokawa.databinding.FragmentUpcomingMovieBinding
import com.kurokawa.view.activities.MovieDetailActivity
import com.kurokawa.viewModel.MovieListViewModel
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
        viewModel.filteredMovies.observe(viewLifecycleOwner) { filteredList ->
            Log.e(
                "ALL-MOVIES-FRAGMENT",
                "Actualizando RecyclerView con ${filteredList.size} películas"
            )
            val uniqueList = filteredList.distinctBy { it.idMovie }
            adapter.updateData(uniqueList)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun getMovies() {
        val upcomingListMovies = viewModel.getMovieByCategory("Upcoming").observe(viewLifecycleOwner){ umpcomingMovies->
            adapter.submitList(umpcomingMovies)
        }
    }


    override fun navigateToMovieDetail(movieDetail: MovieEntity) {
        val intent = Intent(requireContext(), MovieDetailActivity::class.java)
        intent.putExtra("MOVIE", movieDetail )
        startActivity(intent)
    }

}