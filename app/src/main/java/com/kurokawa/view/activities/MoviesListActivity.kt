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
import com.kurokawa.data.paperDB.adapter.MoviesListAdapter
import com.kurokawa.data.paperDB.entities.MovieEntity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListActivity : AppCompatActivity() {
    /** VARIABLES- BINDING - ADAPTER- VIEWMODEL-NAVCONTROLLER-------------------------------------*/
    private lateinit var _binding: ActivityMoviesListBinding
    private val binding: ActivityMoviesListBinding get() = _binding
    private lateinit var adapter: MoviesListAdapter
    private val viewModel: MovieListViewModel by viewModel()
    private lateinit var navController: NavController

    /**MAIN---------------------------------------------------------------------------------------*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMoviesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Llamamos las funciones para inicializar variables e imprimir las movies
        inicializarUI()
        loadInitialData()
    }

    /**FUNCIONES----------------------------------------------------------------------------------*/
    //Inicializa adapter - toolbar- navHostFragment
    private fun inicializarUI() {
        //Adapter hasta la vista detalles con  la movie seleccionada
        adapter = MoviesListAdapter(mutableListOf()) { movieDetail ->
            navigaToDetail(movieDetail)
        }
        //Toolbar
        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = ""

        //NavhostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_graph_movies) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.popularMovieFragment -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    binding.toolbar.menu.findItem(R.id.menu_all_movies)?.isVisible = true
                    supportActionBar?.title = "Películas Populares" // Título específico
                }
                R.id.topRatedMovieFragment -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    binding.toolbar.menu.findItem(R.id.menu_all_movies)?.isVisible = true
                    supportActionBar?.title = "Películas Mejor Valoradas"
                }
                R.id.upcomingMovieFragment -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    binding.toolbar.menu.findItem(R.id.menu_all_movies)?.isVisible = true
                    supportActionBar?.title = "Próximos Estrenos"
                }
                R.id.nowPlayingMovieFragment -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    binding.toolbar.menu.findItem(R.id.menu_all_movies)?.isVisible = true
                    supportActionBar?.title = "Películas en Cartelera"
                }
                R.id.favoriteMovieFragment -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    binding.toolbar.menu.findItem(R.id.menu_all_movies)?.isVisible = true
                    supportActionBar?.title = "Mis Favoritas"
                }
                R.id.allMoviesFragment -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    binding.toolbar.menu.findItem(R.id.menu_all_movies)?.isVisible = false
                    supportActionBar?.title = "Todas las Películas"
                }
                else -> {
                    Log.e("MOVIES-LIST-ACTIVITY", "Error al mostrar los items del toolbar")
                }
            }
            // Limpiar el campo de búsqueda
            clearSearchView()


        }

    }

    //Limpia el campo de busqueda de movies
    private fun clearSearchView() {
        val searchItem = binding.toolbar.menu.findItem(R.id.menu_search)
        val searchView = searchItem?.actionView as? androidx.appcompat.widget.SearchView
        searchView?.setQuery("", false) //Borra el texto de búsqueda
        searchView?.clearFocus() // Evita que el teclado siga abierto
        searchView?.onActionViewCollapsed() // Cierra la barra de búsqueda si estaba expandida
    }

    //Navegar con la movie seleccionada  a la vista detail
    private fun navigaToDetail(movieDetail: MovieEntity) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("MOVIE", movieDetail)//Movie que recibira la MovieDetail Activity
        startActivity(intent)
    }

    //Carga la lista de todas las movies de shared, si es empty realiza una nueva consulta a la API
    private fun loadInitialData() {
        val  moviesList = viewModel.loadAllMovies()
            if (moviesList != null) {
                loadMoviesApi()
            } else {
                adapter.submitList(moviesList)
                viewModel.filterMovies("")
            }
        }


   //Carga las movies desde la API y las contiene en una misma lista
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

    //Navega al fragmento según la opcion del toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

            return when (item.itemId) {
                R.id.menu_favorites -> navigateTo(R.id.favoriteMovieFragment)
                R.id.menu_populares -> navigateTo(R.id.popularMovieFragment)
                R.id.menu_top_rated -> navigateTo(R.id.topRatedMovieFragment)
                R.id.menu_upcomming -> navigateTo(R.id.upcomingMovieFragment)
                R.id.menu_now_playing -> navigateTo(R.id.nowPlayingMovieFragment)
                R.id.menu_all_movies -> navigateTo(R.id.allMoviesFragment)
                else -> super.onOptionsItemSelected(item)
            }


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    //Permite la navegacion de los fragmentos  en todas las direcciones
    private fun navigateTo(destination: Int): Boolean {
        return if (navController.currentDestination?.id != destination) {
            navController.navigate(destination)
            true
        } else {
            false
        }
    }


    //Configuracion del TOOLBAR FIlTRAR movies
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)//Infla el menu toolbar
        val searchItem = menu?.findItem(R.id.menu_search) //Le damos el menu
        val searchView = searchItem?.actionView as? androidx.appcompat.widget.SearchView

        if (searchView == null) {
            Log.e("MOVIE-LIST-ACTIVITY", "SearchView no encontrado en el menú")
            return true
        }

        //Escucha lo que el usuario esta escribiendo
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.filterMovies(query ?: "")
                Log.e("MOVI-LIST-ACTIVITY","$query")
                return false
            }

            //Filtre unicamente movies favoritas en el Favoritefragmento y todas las movies en cualquier fragment
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


}



