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




    /**PRIVATE Y PUBLICAS PARA ACTUALIZAR LIVEDATA------------------------------------------------*/
    private val _popularMovie = MutableLiveData<List<MovieModel>?>()
    val popularMovie : LiveData<List<MovieModel>?> = _popularMovie

    private val _topRatedMovie = MutableLiveData<List<MovieModel>>()
    val topRatedMovie : LiveData<List<MovieModel>> = _topRatedMovie

    private val _nowPlayingMovie = MutableLiveData<List<MovieModel>>()
    val nowPlayingMovie : LiveData<List<MovieModel>> = _nowPlayingMovie

    private val _upcomingMovie = MutableLiveData<List<MovieModel>>()
    val upcomingMovie : LiveData<List<MovieModel>> = _upcomingMovie

    /**VAL PRIVATE Y PUBLICAS PARA ACTUALIZAR ROOM------------------------------------------------*/

    val popularMovieRoom : LiveData<List<MovieEntity>> = repository.getPopularRoom()
    val topRatedMovieRoom : LiveData<List<MovieEntity>> = repository.getTopRatedRoom()
    val nowPlayingMovieRoom : LiveData<List<MovieEntity>> = repository.getNowPlayingRoom()
    val upcomingMovieRoom : LiveData<List<MovieEntity>> = repository.getUpcomingRoom()
    val getAllMoviesRoom: LiveData<List<MovieEntity>> = repository.getAllMoviesRoom()
    val getAllFavoritesRoom: LiveData<List<MovieEntity>> = repository.getAllFavoriteMoviesRoom()




    /**FUNCIONES PARA CARGAR MOVIES POR CATEGORIA-------------------------------------------------*/
    /**POPULAR*/
   fun loadPopularMovies(page: Int){
        viewModelScope.launch(Dispatchers.IO){
            val movies = repository.getPopularMovie(apiKey,page)
            if (movies != null){
                withContext(Dispatchers.Main){
                    _popularMovie.value = movies

                    //Console
                    showMoviesConsole= movies
                    showMoviesToConsole()
                }
            }else{
                withContext(Dispatchers.Main){
                    _popularMovie.value =  emptyList()

                    //Console
                    showErrorToConsole()
                }
            }
        }
    }
    /**MEJOR VALORADAS TOP-RATED */
    fun loadTopRateMovies(page: Int){
        viewModelScope.launch(Dispatchers.IO){
            val movies = repository.getTopRatedMovie(apiKey,page)
            if (movies != null){
                withContext(Dispatchers.Main){
                    _topRatedMovie.value = movies!!
                    showMoviesConsole= movies
                    showMoviesToConsole()
                }
            }else{
                withContext(Dispatchers.Main){
                    _topRatedMovie.value =  emptyList()
                    showErrorToConsole()
                }
            }
        }
    }

    /**EN CARTELERA NOW-PLAYING*/
    fun loadNowPlayingMovies(page: Int){
        viewModelScope.launch(Dispatchers.IO){
            val movies = repository.getNowPlayingMovie(apiKey,page)
            if (movies != null){
                withContext(Dispatchers.Main){
                    _nowPlayingMovie.value = movies!!
                    showMoviesConsole= movies
                    showMoviesToConsole()
                }
            }else{
                withContext(Dispatchers.Main){
                    _nowPlayingMovie.value =  emptyList()
                  showErrorToConsole()
                }
            }
        }
    }


    /**PROXIMOS ESTRENOS UPCOMING */
    fun loadUpcommingMovies(page: Int){
        viewModelScope.launch(Dispatchers.IO){
            val movies = repository.getUpcomingMovie(apiKey,page)
            if (movies != null){
                withContext(Dispatchers.Main){
                    _upcomingMovie.value = movies!!
                    showMoviesConsole= movies
                    showMoviesToConsole()
                }
            }else{
                withContext(Dispatchers.Main){
                    _upcomingMovie.value =  emptyList()
                    showErrorToConsole()
                }
            }
        }

    }




    /**FUNCIONES PARA MOSTRAR LAS PELICULAS Y ERRORES POR CONSOLA---------------------------------*/
    /**CARGAR LAS MOVIES */
    fun showMoviesToConsole(){
        Log.e("MOVIELISTREPOSITORY ", "se recibieron las movies :$showMoviesConsole ")
    }

    /**ERROR CARGAR MOVIES */
    fun showErrorToConsole(){
        Log.e("ERROR AL CARGAR MOVIES","Error al obtener las movies")
    }



}
