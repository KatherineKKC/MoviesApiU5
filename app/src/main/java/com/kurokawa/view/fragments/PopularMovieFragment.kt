package com.kurokawa.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.kurokawa.data.room.adapter.MoviesListAdapter
import com.kurokawa.data.room.entities.MovieEntity
import com.kurokawa.databinding.FragmentPopularMovieBinding
import com.kurokawa.view.activities.MovieDetailActivity
import com.kurokawa.viewModel.MovieListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PopularMovieFragment : Fragment(), FragmentMetodos {
    /**VARIABLES DECLARADAS-----------------------------------------------------------------------*/
    private lateinit var _binding: FragmentPopularMovieBinding
    private val binding: FragmentPopularMovieBinding get() = _binding
    private lateinit var adapter: MoviesListAdapter
    private val viewModel: MovieListViewModel by sharedViewModel()

    /**VISTA--------------------------------------------------------------------------------------*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPopularMovieBinding.inflate(inflater)
        return binding.root
    }

    /**MAIN---------------------------------------------------------------------------------------*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        getMovies()
        observerFilter()
    }

    /**FUNCIONES----------------------------------------------------------------------------------*/
    override fun setupRecycler() {
        adapter = MoviesListAdapter(mutableListOf()) { movieDetail ->
            navigateToMovieDetail(movieDetail)
        }
        binding.recyclerViewPopular.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewPopular.adapter = adapter
    }

    override fun observerFilter() {
        viewModel.filteredMovies.observe(viewLifecycleOwner) { filteredList ->
            Log.e(
                "ALL-MOVIES-FRAGMENT",
                "Actualizando RecyclerView con ${filteredList.size} películas"
            )
            val uniqueList = filteredList.distinctBy { it.idMovie }
            adapter.submitList(uniqueList)
        }

    }

    override fun getMovies() {
        viewModel.getMovieByCategory("Popular").observe(viewLifecycleOwner) { nowPlayingMoveList ->
            val uniqueList = nowPlayingMoveList.distinctBy { it.idMovie }
            adapter.submitList(uniqueList)
        }

    }

    override fun navigateToMovieDetail(movieDetail: MovieEntity) {
        val intent = Intent(requireContext(), MovieDetailActivity::class.java)
        intent.putExtra("MOVIE", movieDetail)
        startActivity(intent)
    }
}