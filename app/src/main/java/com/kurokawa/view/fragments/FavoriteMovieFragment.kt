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
import com.kurokawa.databinding.FragmentFavoriteMovieBinding
import com.kurokawa.view.activities.MovieDetailActivity
import com.kurokawa.viewModel.MovieListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FavoriteMovieFragment : Fragment() {
    private lateinit var _binding : FragmentFavoriteMovieBinding
    private val binding: FragmentFavoriteMovieBinding get() = _binding

    private lateinit var adapter: MoviesListAdapter
    private val viewModel : MovieListViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteMovieBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**INICIALIZACION DE ADAPTER */

        setupRecycler()
        getAllFavoritesMovies()
        observerFilter()

    }





    /**FUNCIONES----------------------------------------------------------------------------------*/
    /**INICIALIZACION DE ADAPTER Y RECYCLER*/

    private fun setupRecycler() {
        adapter = MoviesListAdapter(mutableListOf()) { movie ->
            navigateToMovieDetail(movie)
        }
        binding.recyclerViewFavorites.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewFavorites.adapter = adapter
    }




    private fun observerFilter() {
        viewModel.filteredFavorites.observe(viewLifecycleOwner) { filteredList ->
            Log.e(
                "ALL-MOVIES-FRAGMENT",
                "Actualizando RecyclerView con ${filteredList.size} películas"
            )
            val uniqueList = filteredList.distinctBy { it.idMovie }
            adapter.submitList(uniqueList)
        }
    }

    private fun navigateToMovieDetail(movieSelected: MovieEntity) {
        val intent = Intent(requireContext(), MovieDetailActivity::class.java)
        intent.putExtra("MOVIE", movieSelected)
        startActivity(intent)
        Log.e("MOVIES-LIST-ACTIVITY", "La movie enviada a la activity detail es: $movieSelected ")
    }

    private fun getAllFavoritesMovies() {
        viewModel.getAllFavoriteMovies.observe(viewLifecycleOwner) { movies ->
            val uniqueList = movies.distinctBy { it.idMovie }
            Log.e("ALL-MOVIES-FRAGMENT", "Recibiendo de viewModel ${uniqueList.size} películas")
            adapter.submitList(uniqueList)
        }
    }
}