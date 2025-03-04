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
import com.kurokawa.databinding.FragmentNowPlayingMovieBinding
import com.kurokawa.view.activities.MovieDetailActivity
import com.kurokawa.viewModel.MovieListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class NowPlayingMovieFragment : Fragment(), FragmentMetodos {
    /**VARIABLES DECLARADAS-----------------------------------------------------------------------*/
    private lateinit var _binding: FragmentNowPlayingMovieBinding
    private val binding: FragmentNowPlayingMovieBinding get() = _binding
    private lateinit var adapter: MoviesListAdapter
    private val viewModel: MovieListViewModel by sharedViewModel()

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
    override fun setupRecycler() {
        adapter = MoviesListAdapter(mutableListOf()) { movieDetail ->
            navigateToMovieDetail(movieDetail)
        }
        binding.recyclereViewNowPlaying.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclereViewNowPlaying.adapter = adapter
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
        lifecycleScope.launch {
            viewModel.getMovieByCategory("NowPlaying").collect { movies ->
                val uniqueList = movies.distinctBy { it.idMovie }
                withContext(Dispatchers.Main) { // ✅ Cambia al hilo principal
                    adapter.submitList(uniqueList) // ✅ Ahora es seguro tocar la UI
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