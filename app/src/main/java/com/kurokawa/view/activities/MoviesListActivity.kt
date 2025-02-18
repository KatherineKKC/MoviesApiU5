package com.kurokawa.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.kurokawa.databinding.ActivityMoviesListBinding
import com.kurokawa.model.MovieModel
import com.kurokawa.viewModel.MovieListViewModel
import com.kurokawa.R
import com.kurokawa.data.room.adapter.MoviesListAdapter
import com.kurokawa.data.room.entities.MovieEntity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListActivity : AppCompatActivity(){
    /** VARIABLE PARA VIEW BINDING */
    private lateinit var _binding: ActivityMoviesListBinding
    private val binding: ActivityMoviesListBinding get() = _binding
    private lateinit var adapter: MoviesListAdapter

    /** INYECCIÓN DE VIEWMODEL */
    private val viewModel: MovieListViewModel by viewModel()

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
        adapter = MoviesListAdapter(mutableListOf()) { movieDetail ->
            navigaToDetail(movieDetail)
        }
        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = ""

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_graph_movies) as NavHostFragment
        navController = navHostFragment.navController


        navController.addOnDestinationChangedListener { _, _, _ ->
            clearSearchView()
        }


    }

    private fun clearSearchView() {
        val searchItem = binding.toolbar.menu.findItem(R.id.menu_search)
        val searchView = searchItem?.actionView as? androidx.appcompat.widget.SearchView
        searchView?.setQuery("", false) // 🔹 Borra el texto de búsqueda
        searchView?.clearFocus() // 🔹 Evita que el teclado siga abierto
        searchView?.onActionViewCollapsed() // 🔹 Cierra la barra de búsqueda si estaba expandida
    }


    private fun navigaToDetail(movieDetail: MovieEntity) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("MOVIE", movieDetail)
        startActivity(intent)
    }


    /** CARGAR DATOS INICIALES */
    private fun loadInitialData() {
      viewModel.loadAllMovies()
      viewModel.getAllMovies.observe(this) { allMovies ->
            if (allMovies.isNullOrEmpty()) {
                loadMoviesApi()
            }else{
             viewModel.filterMovies("")
            }
        }
    }



    /** CARGAR PELÍCULAS DESDE LA API */
    private fun loadMoviesApi() {
      viewModel.loadAllMovies()

        val liveDataList = listOf(
          viewModel.popularMovie,
          viewModel.topRatedMovie,
          viewModel.nowPlayingMovie,
          viewModel.upcomingMovie
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

        val searchItem = menu?.findItem(R.id.menu_search) // Asegúrate de que el ID sea correcto
        val searchView = searchItem?.actionView as? androidx.appcompat.widget.SearchView // 🔹 Casting seguro

        if (searchView == null) {
            Log.e("MOVIE-LIST-ACTIVITY", "SearchView no encontrado en el menú")
            return true
        }else{

        }

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
              viewModel.filterMovies(query ?: "")
                return false
            }

           
            override fun onQueryTextChange(newText: String?): Boolean {
                val currentFragment = navController.currentDestination?.id
                if (currentFragment == R.id.favoriteMovieFragment) {
                    viewModel.filterFavorites(newText ?: "")
                } else {
                    viewModel.filterMovies(newText ?: "")
                }
                return true
            }

        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorites -> navigateTo(R.id.favoriteMovieFragment)
            R.id.menu_populares -> navigateTo(R.id.popularMovieFragment)
            R.id.menu_top_rated -> navigateTo(R.id.topRatedMovieFragment)
            R.id.menu_upcomming -> navigateTo(R.id.upcomingMovieFragment)
            R.id.menu_now_playing -> navigateTo(R.id.nowPlayingMovieFragment)
            else -> super.onOptionsItemSelected(item)
        }
    }

    /** Función reutilizable para navegar entre fragmentos */
    private fun navigateTo(destination: Int): Boolean {
        if (navController.currentDestination?.id != destination) {
            navController.navigate(destination)
            return true
        }
        return false
    }







}



