package com.kurokawa.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurokawa.repository.MovieListRepository
import com.kurokawa.data.room.entities.MovieEntity
import com.kurokawa.model.MovieModel
import com.kurokawa.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListViewModel(private val repository: MovieListRepository) : ViewModel() {
    /**API----------------------------------------------------------------------------------------*/
    val apiKey = Constants.API_KEY

    /**VARIABLES LIVE DATA------------------------------------------------------------------------*/
    //Obtener Todas las movies Favoritas y todas las movies en total
    val getAllMovies: LiveData<List<MovieEntity>> = repository.getAllMoviesRoom()

    //Obtiene las filtraciones de busqueda de todas las movies
    private val _filteredMovies = MutableLiveData<List<MovieEntity>>()
    val filteredMovies: LiveData<List<MovieEntity>> get() = _filteredMovies

    //Obtiene las filtraciones de busqueda de todas las favoritas
    private val _filteredFavorites = MutableLiveData<List<MovieEntity>>()
    val filteredFavorites: LiveData<List<MovieEntity>> get() = _filteredFavorites

    //Obtiene movies por categoria
    //popular
    private val _popularMovie = MutableLiveData<List<MovieModel>?>()
    val popularMovie: LiveData<List<MovieModel>?> = _popularMovie

    //Mas valorada
    private val _topRatedMovie = MutableLiveData<List<MovieModel>?>()
    val topRatedMovie: LiveData<List<MovieModel>?> = _topRatedMovie

    //En cartelera
    private val _nowPlayingMovie = MutableLiveData<List<MovieModel>?>()
    val nowPlayingMovie: LiveData<List<MovieModel>?> = _nowPlayingMovie

    //Proximas
    private val _upcomingMovie = MutableLiveData<List<MovieModel>?>()
    val upcomingMovie: LiveData<List<MovieModel>?> = _upcomingMovie


    /**FUNCIONES PARA FILTRAR---------------------------------------------------------------------*/
    //Recibe el texto introducido en la barra de busqueda y filtra TODAS las movies
    fun filterMovies(query: String) {
        val allMovies = getAllMovies.value ?: emptyList()
        _filteredMovies.value = if (query.isEmpty()) {
            allMovies
        } else {
            allMovies.filter { it.title.contains(query, ignoreCase = true) }
        }
    }

    //Recibe el texto introducido en la barra de busqueda y filtra las movies Favoritas
    fun filterFavorites(query: String) {
        viewModelScope.launch(Dispatchers.IO){
            val allFavorites = repository.getAllFavoriteMovies().value ?: emptyList() // 🔹 Filtra SOLO favoritos
            withContext(Dispatchers.Main){
                if (allFavorites != null){
                    _filteredFavorites.value = if (query.isEmpty()) {
                        allFavorites
                }else {
                        allFavorites.filter { it.title.contains(query, ignoreCase = true) }
                    }
            }
          }
        }
    }




    /**FUNCION PARA ACTIVAR LA CARGA DE  TODAS LAS CATEGORIAS DE MOVIES DE LA API ----------------*/
    fun loadAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val popular = repository.getPopularMovie(apiKey, 1)
            val topRated = repository.getTopRatedMovie(apiKey, 1)
            val nowPlaying = repository.getNowPlayingMovie(apiKey, 1)
            val upcoming = repository.getUpcomingMovie(apiKey, 1)

            withContext(Dispatchers.Main) {
                if (!popular.isNullOrEmpty() && !topRated.isNullOrEmpty() && !nowPlaying.isNullOrEmpty() && !upcoming.isNullOrEmpty()) {
                    //Se actualizan los valores
                    _popularMovie.value = popular
                    _topRatedMovie.value = topRated
                    _nowPlayingMovie.value = nowPlaying
                    _upcomingMovie.value = upcoming
                } else {
                    //Evita errores null
                    _popularMovie.value = emptyList()
                    _topRatedMovie.value = emptyList()
                    _nowPlayingMovie.value = emptyList()
                    _upcomingMovie.value = emptyList()

                    //Console
                    showErrorToConsole()

                }
            }

        }
    }

    /**FUNCION PARA OBTENER LAS CATEGORIAS DESDE ROOM---------------------------------------------*/
    fun getMovieByCategory(category: String): LiveData<List<MovieEntity>> {
        return repository.getByCategory(category)
    }

    /**FUNCION CONSOLE----------------------------------------------------------------------------*/
    fun showErrorToConsole() {
        Log.e("MOVIE-LIST-VIEW-MODEL", "Se ha recibido una lista Vacia de la fun loadAllMovies")
    }


}


