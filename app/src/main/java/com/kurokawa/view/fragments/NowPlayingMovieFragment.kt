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
import com.kurokawa.data.paperDB.adapter.MoviesListAdapter
import com.kurokawa.data.paperDB.entities.MovieEntity
import com.kurokawa.databinding.FragmentNowPlayingMovieBinding
import com.kurokawa.view.activities.MovieDetailActivity
import com.kurokawa.viewModel.MovieListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class NowPlayingMovieFragment : Fragment(),FragmentMetodos {
    /**VARIABLES DECLARADAS-----------------------------------------------------------------------*/
    private lateinit var _binding : FragmentNowPlayingMovieBinding
    private val binding: FragmentNowPlayingMovieBinding get() = _binding
    private lateinit var adapter: MoviesListAdapter
    private val viewModel : MovieListViewModel by sharedViewModel()

    /**VISTA--------------------------------------------------------------------------------------*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNowPlayingMovieBinding.inflate(inflater)
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
    override fun setupRecycler(){
        adapter = MoviesListAdapter(mutableListOf()) { movieDetail ->
            navigateToMovieDetail(movieDetail)
        }
        binding.recyclereViewNowPlaying.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclereViewNowPlaying.adapter = adapter
    }

   override fun observerFilter(){
        viewModel.filteredMovies.observe(viewLifecycleOwner) { filteredList ->
            Log.e("ALL-MOVIES-FRAGMENT", "Actualizando RecyclerView con ${filteredList.size} películas")
            val uniqueList = filteredList.distinctBy { it.idMovie }
            adapter.submitList(uniqueList)
        }
    }

   @SuppressLint("SuspiciousIndentation")
   override fun getMovies(){
        viewModel.getMovieByCategory("NowPlaying").observe(viewLifecycleOwner) { moviesNowPlaying ->
            viewModel.filterMovies("")
           adapter.submitList(moviesNowPlaying)
       }
    }

   override fun navigateToMovieDetail(movieDetail: MovieEntity) {
        val intent = Intent(requireContext(), MovieDetailActivity::class.java)
        intent.putExtra("MOVIE", movieDetail)
        startActivity(intent)
    }
}