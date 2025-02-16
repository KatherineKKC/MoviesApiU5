package com.kurokawa.view.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.kurokawa.databinding.ActivityMoviesListBinding
import com.kurokawa.model.MovieModel
import com.kurokawa.viewModel.MovieListViewModel
import com.kurokawa.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListActivity : AppCompatActivity() {
    /** VARIABLE PARA VIEW BINDING */
    private lateinit var _binding: ActivityMoviesListBinding
    private val binding: ActivityMoviesListBinding get() = _binding

    /** INYECCIÓN DE VIEWMODEL */
    private val movieViewModel: MovieListViewModel by viewModel()

    /** CONTROLADOR DE NAVEGACIÓN */
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMoviesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inicializarUI()
        loadInitialData()
    }

    /** CONFIGURAR TOOLBAR Y NAVIGATION */
    private fun inicializarUI() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = ""

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_graph_movies) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.toolbar, navController)
    }

    /** CARGAR DATOS INICIALES */
    private fun loadInitialData() {
        movieViewModel.loadAllMovies()
        movieViewModel.getAllMovies.observe(this) { allMovies ->
            if (allMovies.isNullOrEmpty()) {
                loadMoviesApi()
            }
        }
    }

    /** CARGAR PELÍCULAS DESDE LA API */
    private fun loadMoviesApi() {
        movieViewModel.loadAllMovies()

        val liveDataList = listOf(
            movieViewModel.popularMovie,
            movieViewModel.topRatedMovie,
            movieViewModel.nowPlayingMovie,
            movieViewModel.upcomingMovie
        )

        val moviesResults = mutableListOf<List<MovieModel>?>()

        liveDataList.forEach { liveData ->
            liveData.observe(this) { moviesApi ->
                if (!moviesApi.isNullOrEmpty()) {
                    moviesResults.add(moviesApi)
                    if (moviesResults.size == liveDataList.size) {
                        Log.d("MOVIE-LIST-ACTIVITY", "Datos API recibidos, actualizando UI...")
                    }
                }
            }
        }
    }

    /** CONFIGURACIÓN DEL MENÚ TOOLBAR */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorites -> {
                navigateToFavorite()
                true
            }
            R.id.menu_populares -> {
                navigateToPopular()
                true
            }
            R.id.menu_top_rated -> {
                navigateToTopRated()
                true
            }
            R.id.menu_upcomming -> {
                navigateToUpcomming()
                true
            }
            R.id.menu_now_playing -> {
                navigateToNowPlaying()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /** FUNCIONES DE NAVEGACIÓN */
    private fun navigateToPopular() {
        navController.navigate(R.id.action_allMoviesFragment_to_popularMovieFragment)
    }

    private fun navigateToFavorite() {
        navController.navigate(R.id.action_allMoviesFragment_to_favoriteMovieFragment)
    }

    private fun navigateToNowPlaying() {
        navController.navigate(R.id.action_allMoviesFragment_to_nowPlayingMovieFragment)
    }

    private fun navigateToTopRated() {
        navController.navigate(R.id.action_allMoviesFragment_to_topRatedMovieFragment)
    }

    private fun navigateToUpcomming() {
        navController.navigate(R.id.action_allMoviesFragment_to_upcomingMovieFragment)
    }
}
