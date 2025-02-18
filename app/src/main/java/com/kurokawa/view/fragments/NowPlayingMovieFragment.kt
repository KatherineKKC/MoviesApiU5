package com.kurokawa.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.kurokawa.data.room.adapter.MoviesListAdapter
import com.kurokawa.data.room.entities.MovieEntity
import com.kurokawa.databinding.FragmentNowPlayingMovieBinding
import com.kurokawa.view.activities.MovieDetailActivity
import com.kurokawa.viewModel.MovieListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class NowPlayingMovieFragment : Fragment() {
    private lateinit var _binding : FragmentNowPlayingMovieBinding
    private val binding: FragmentNowPlayingMovieBinding get() = _binding


    private lateinit var adapter: MoviesListAdapter
    private val viewModel : MovieListViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNowPlayingMovieBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecicler()
        getAllNowPlayingMovies()
        observerFilter()
    }
    private fun setupRecicler(){
        adapter = MoviesListAdapter(mutableListOf()) { movieDetail ->
            navigateToMovieDetail(movieDetail)
        }
        binding.recyclereViewNowPlaying.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclereViewNowPlaying.adapter = adapter
    }


    private fun navigateToMovieDetail(movieDetail: MovieEntity) {
        val intent = Intent(requireContext(), MovieDetailActivity::class.java)
        intent.putExtra("MOVIE", movieDetail)
        startActivity(intent)
    }

    private fun observerFilter(){
        viewModel.filteredMovies.observe(viewLifecycleOwner) { filteredList ->
            Log.e("ALL-MOVIES-FRAGMENT", "Actualizando RecyclerView con ${filteredList.size} películas")
            val uniqueList = filteredList.distinctBy { it.idMovie }
            adapter.submitList(uniqueList)
        }

    }
    private fun getAllNowPlayingMovies(){
        viewModel.getMovieByCategory("NowPlaying").observe(viewLifecycleOwner){ nowPlayingMoveList->
            val uniqueList = nowPlayingMoveList.distinctBy { it.idMovie }
            adapter.submitList(uniqueList)
        }

    }

}