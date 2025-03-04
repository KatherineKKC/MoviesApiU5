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
import com.kurokawa.databinding.FragmentAllMoviesBinding
import com.kurokawa.view.activities.MovieDetailActivity
import com.kurokawa.viewModel.MovieListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AllMoviesFragment : Fragment(), FragmentMetodos {
    /**VARIABLES DECLARADAS-----------------------------------------------------------------------*/
    private lateinit var _binding : FragmentAllMoviesBinding
    private val binding: FragmentAllMoviesBinding get() = _binding

    private lateinit var adapter: MoviesListAdapter
    private val allViewModel : MovieListViewModel by  sharedViewModel()


    /**VISTA--------------------------------------------------------------------------------------*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAllMoviesBinding.inflate(inflater)
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
        adapter = MoviesListAdapter(mutableListOf()) { movie ->
            navigateToMovieDetail(movie)
        }
        binding.recyclerViewAll.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewAll.adapter = adapter
    }

    override fun getMovies() {
        lifecycleScope.launch(Dispatchers.IO) { // ✅ Ejecuta el código en un hilo de fondo
            allViewModel.loadAllMovies()
            allViewModel.allMovies.collect { movies ->
                val uniqueList = movies.distinctBy { it.idMovie }
                withContext(Dispatchers.Main) { // ✅ Cambia al hilo principal
                    adapter.submitList(uniqueList) // ✅ Ahora es seguro tocar la UI
                }
            }
        }
    }

    override fun observerFilter() {
        lifecycleScope.launch {
            allViewModel.filteredMovies.collectLatest { filteredList ->
                Log.e("ALL-MOVIES-FRAGMENT", "Filtrando  ${filteredList.size} películas")

                val uniqueList = filteredList.distinctBy { it.idMovie } // 🔹 Evita duplicados

                adapter.submitList(uniqueList) // 🔹 Se ejecuta en el hilo principal
            }
        }
    }


    override fun navigateToMovieDetail(movieDetail: MovieEntity) {
        val intent = Intent(requireContext(), MovieDetailActivity::class.java)
        intent.putExtra("MOVIE", movieDetail)
        startActivity(intent)
    }
}
