package com.kurokawa.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.kurokawa.data.dataStore.adapter.MoviesListAdapter
import com.kurokawa.data.dataStore.entities.MovieEntity
import com.kurokawa.databinding.FragmentFavoriteMovieBinding
import com.kurokawa.view.activities.MovieDetailActivity
import com.kurokawa.viewModel.MovieListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FavoriteMovieFragment : Fragment(),FragmentMetodos {
    /**VARIABLES DECLARADAS-----------------------------------------------------------------------*/
    private lateinit var _binding : FragmentFavoriteMovieBinding
    private val binding: FragmentFavoriteMovieBinding get() = _binding
    private lateinit var adapter: MoviesListAdapter
    private val viewModel : MovieListViewModel by sharedViewModel()

    /**VISTA--------------------------------------------------------------------------------------*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteMovieBinding.inflate(inflater)
        return binding.root
    }

    /**MAIN---------------------------------------------------------------------------------------*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Funciones llamadas
        setupRecycler()
        getMovies()
        observerFilter()

    }

    /**FUNCIONES----------------------------------------------------------------------------------*/
    override fun setupRecycler() {
        adapter = MoviesListAdapter(mutableListOf()) { movie ->
            navigateToMovieDetail(movie)
        }
        binding.recyclerViewFavorites.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewFavorites.adapter = adapter
    }

    override fun observerFilter() {
        lifecycleScope.launch {
            viewModel.observeFavorites()
            viewModel.filteredFavorites.collectLatest { filteredList ->
                Log.e("ALL-MOVIES-FRAGMENT", "Actualizando RecyclerView con ${filteredList.size} películas")

                val uniqueList = filteredList.distinctBy { it.idMovie } // 🔹 Evita duplicados
                adapter.submitList(uniqueList) // 🔹 Se ejecuta en el hilo principal
            }
        }
    }
  override fun getMovies() {

      lifecycleScope.launch(Dispatchers.IO){
          viewModel.loadAllFavorites()
          viewModel.allFavoriteMovies.collectLatest{ movies ->
              val uniqueList = movies.distinctBy { it.idMovie }
              Log.e("FAVORITES-MOVIES-FRAGMENT", "Recibiendo la lista de favoritas desde el MovieListViewModel ${uniqueList.size} películas")
              withContext(Dispatchers.Main){
                  adapter.submitList(uniqueList)
              }
          }
      }
    }

   override fun navigateToMovieDetail(movieDetail: MovieEntity) {
        val intent = Intent(requireContext(), MovieDetailActivity::class.java)
        intent.putExtra("MOVIE", movieDetail)
        startActivity(intent)
        Log.e("MOVIES-LIST-ACTIVITY", "La movie enviada a la activity detail es: $movieDetail")
    }

}