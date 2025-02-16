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

class MovieListViewModel (private val repository: MovieListRepository) : ViewModel() {


    /**APY Y MOVIES POR CONSOLA-------------------------------------------------------------------*/
    val apiKey = Constants.API_KEY

    /**LIVE DATA PARA OBTENER LAS FAVORITAS ------------------------------------------------------*/
    val getAllFavoriteMovies: LiveData<List<MovieEntity>> = repository.getAllFavoriteMovies()
    val getAllMovies: LiveData<List<MovieEntity>> = repository.getAllMoviesRoom()

    /**LIVEDATA PARA ACTUALIZAR TODAS DESDE ROOM--------------------------------------------------*/

    /**LIVE DATA PARA ACTUALIZAR CADA CATEGORIA---------------------------------------------------*/
    private val _popularMovie = MutableLiveData<List<MovieModel>?>()
    val popularMovie: LiveData<List<MovieModel>?> = _popularMovie

    private val _topRatedMovie = MutableLiveData<List<MovieModel>?>()
    val topRatedMovie: LiveData<List<MovieModel>?> = _topRatedMovie

    private val _nowPlayingMovie = MutableLiveData<List<MovieModel>?>()
    val nowPlayingMovie: LiveData<List<MovieModel>?> = _nowPlayingMovie

    private val _upcomingMovie = MutableLiveData<List<MovieModel>?>()
    val upcomingMovie: LiveData<List<MovieModel>?> = _upcomingMovie


    /**FUNCION PARA ACTIVAR LA CARGA DE  TODAS LAS CATEGORIAS DE MOVIES DE LA API ----------------*/
    /**POPULAR*/
    fun loadAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val popular = repository.getPopularMovie(apiKey, 1)
            val topRated = repository.getTopRatedMovie(apiKey, 1)
            val nowPlaying = repository.getNowPlayingMovie(apiKey, 1)
            val upcoming = repository.getUpcomingMovie(apiKey, 1)

            withContext(Dispatchers.Main){
                if (!popular.isNullOrEmpty() && !topRated.isNullOrEmpty() && !nowPlaying.isNullOrEmpty() && !upcoming.isNullOrEmpty()) {

                    _popularMovie.value = popular
                    _topRatedMovie.value = topRated
                    _nowPlayingMovie.value = nowPlaying
                    _upcomingMovie.value = upcoming
                    //Console
                } else {
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

    /**ERROR CARGAR MOVIES */
    fun showErrorToConsole() {
        Log.e("MOVIE-LIST-VIEW-MODEL", "Se ha recibido una lista Vacia de la fun loadAllMovies")
    }


}