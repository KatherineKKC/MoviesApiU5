package com.kurokawa.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurokawa.data.repository.MovieListRepository
import com.kurokawa.data.room.entities.MovieEntity
import com.kurokawa.model.MovieModel
import com.kurokawa.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListViewModel(private val repository: MovieListRepository) : ViewModel() {


    /**APY Y MOVIES POR CONSOLA-------------------------------------------------------------------*/
    val apiKey = Constants.API_KEY
    var showMoviesConsole: List<MovieModel>? = null

    /**LIVEDATA PARA ACTUALIZAR TODAS LAS PELICULAS Y LISTA DE FAVORITAS--------------------------*/
    private val _moviesFromRoom = MutableLiveData<List<MovieEntity>>()
    val moviesFromRoom: LiveData<List<MovieEntity>> get() = _moviesFromRoom


    /**LIVE DATA PARA ACTUALIZAR CADA CATEGORIA---------------------------------------------------*/
    private val _popularMovie = MutableLiveData<List<MovieModel>?>()
    val popularMovie: LiveData<List<MovieModel>?> = _popularMovie

    private val _topRatedMovie = MutableLiveData<List<MovieModel>?>()
    val topRatedMovie: LiveData<List<MovieModel>?> = _topRatedMovie

    private val _nowPlayingMovie = MutableLiveData<List<MovieModel>?>()
    val nowPlayingMovie: LiveData<List<MovieModel>?> = _nowPlayingMovie

    private val _upcomingMovie = MutableLiveData<List<MovieModel>?>()
    val upcomingMovie: LiveData<List<MovieModel>?> = _upcomingMovie


    /**FUNCIONES PARA OBTENER TODAS LAS MOVIES Y TODAS LAS FAVORITAS DE ROOM----------------------*/
    /**TODAS MOVIES*/
    fun getAllMoviesRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            val movies = repository.getAllMoviesRoom()
            Log.e("MOVIE-LIST-VIEW-MODEL", "Películas(todas) obtenidas desde Room: ${movies.size}")

            _moviesFromRoom.postValue(movies)
            Log.e("MOVIE-LIST-VIEW-MODEL", "Películas(todas) obtenidas desde Room: ${movies.size}")
        }
    }


    /**FUNCION PARA ACTIVAR LA CARGA DE  TODAS LAS CATEGORIAS DE MOVIES DE LA API ----------------*/
    /**POPULAR*/
    fun loadAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val popular = repository.getPopularMovie(apiKey, 1)
            val topRated = repository.getTopRatedMovie(apiKey, 1)
            val nowPlaying = repository.getNowPlayingMovie(apiKey, 1)
            val upcoming = repository.getUpcomingMovie(apiKey, 1)

            if (!popular.isNullOrEmpty() && !topRated.isNullOrEmpty() && !nowPlaying.isNullOrEmpty() && !upcoming.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    _popularMovie.value = popular
                    _topRatedMovie.value = topRated
                    _nowPlayingMovie.value = nowPlaying
                    _upcomingMovie.value = upcoming
                    //Console
                    showMoviesConsole = popular + topRated + nowPlaying + upcoming
                    showMoviesToConsole(showMoviesConsole!!)
                }
            } else {
                withContext(Dispatchers.Main) {
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


    /**FUNCIONES PARA MOSTRAR LAS PELICULAS Y ERRORES POR CONSOLA---------------------------------*/
    /**CARGAR LAS MOVIES */
    fun showMoviesToConsole(showMovies: List<MovieModel>) {
        Log.e("MOVIE-LIST-VIEW-MODEL", "se recibieron las movies :${showMoviesConsole?.size} ")
    }

    /**ERROR CARGAR MOVIES */
    fun showErrorToConsole() {
        Log.e("MOVIE-LIST-VIEW-MODEL", "Se ha recibido una lista Vacia de la fun loadAllMovies")
    }


}