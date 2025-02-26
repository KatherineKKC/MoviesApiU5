package com.kurokawa.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurokawa.repository.MovieListRepository
import com.kurokawa.data.dataStore.entities.MovieEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieListViewModel(private val repository: MovieListRepository) : ViewModel() {

    /** 🔹 Estado de las películas */
    private val _allMovies = MutableStateFlow<List<MovieEntity>>(emptyList())
    val allMovies: StateFlow<List<MovieEntity>> get() = _allMovies

    private val _allFavoriteMovies= MutableStateFlow<List<MovieEntity>>(emptyList())
    val allFavoriteMovies: StateFlow<List<MovieEntity>> get() = _allFavoriteMovies

    /** 🔹 Estado de las películas filtradas */
    private val _filteredMovies = MutableStateFlow<List<MovieEntity>>(emptyList())
    val filteredMovies: StateFlow<List<MovieEntity>> get() = _filteredMovies

    /** 🔹 Estado de las películas favoritas filtradas */
    private val _filteredFavorites = MutableStateFlow<List<MovieEntity>>(emptyList())
    val filteredFavorites: StateFlow<List<MovieEntity>> get() = _filteredFavorites



    /** 🔹 Cargar todas las películas desde API o DataStore */
    fun loadAllMovies() {
        viewModelScope.launch {
            try {
                val moviesFromStore = repository.getAllMoviesDataStore().firstOrNull()

                if (moviesFromStore.isNullOrEmpty()) {
                    val popular = repository.getPopularMovie(1)?.let { repository.syncMoviesWithDataStore(it, "Popular") } ?: emptyList()
                    val topRated = repository.getTopRatedMovie(1)?.let { repository.syncMoviesWithDataStore(it, "TopRated") } ?: emptyList()
                    val nowPlaying = repository.getNowPlayingMovie(1)?.let { repository.syncMoviesWithDataStore(it, "NowPlaying") } ?: emptyList()
                    val upcoming = repository.getUpcomingMovie(1)?.let { repository.syncMoviesWithDataStore(it, "Upcoming") } ?: emptyList()

                    // 🔹 Combina las listas y elimina duplicados
                    val allMovies = (popular + topRated + nowPlaying + upcoming).distinctBy { it.idMovie }
                    _allMovies.value = allMovies
                } else {
                    _allMovies.value = moviesFromStore
                }

            } catch (e: Exception) {
                Log.e("MOVIE-LIST-VIEWMODEL", "Error al cargar películas", e)
            }
        }
    }

    fun loadAllFavorites(){
        viewModelScope.launch {
            val movieFavoriteFromStore = repository.getAllFavoriteMovies().firstOrNull()
            if (movieFavoriteFromStore != null){
                _allFavoriteMovies.value = movieFavoriteFromStore
            }else{
                val listFavorites =repository.getAllFavoriteMovies().firstOrNull()
                Log.e("MOVIE-LIST-VIEW-MODEL", "Intenando obtener las movies favoritas del repositorio ${listFavorites?.size}")
            }
        }
    }

    fun observeFavorites() {
        viewModelScope.launch {
            repository.getAllFavoriteMovies().collectLatest { favorites ->
                _allFavoriteMovies.value = favorites
            }
        }
    }

    /** 🔹 Filtrar películas por búsqueda */
    fun filterMovies(query: String) {
        viewModelScope.launch {
            _filteredMovies.value = if (query.isEmpty()) {
                _allMovies.value
            } else {
                _allMovies.value.filter { it.title.contains(query, ignoreCase = true) }
            }
        }
    }

    /** 🔹 Filtrar películas favoritas */
    fun filterFavorites(query: String) {
        viewModelScope.launch {
            _filteredFavorites.value = if (query.isEmpty()) {
                _allFavoriteMovies.value
            } else {
                _allFavoriteMovies.value.filter { it.title.contains(query, ignoreCase = true) }
            }
        }
    }

    /** 🔹 Obtener películas por categoría */
    fun getMovieByCategory(category: String): Flow<List<MovieEntity>> {
        return repository.getByCategory(category)
    }


}
