package com.kurokawa.ui.movieList

import android.graphics.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kurokawa.data.room.model.Movies
import com.kurokawa.repository.MovieRepository

class MovieListViewModel() : ViewModel(){
    private val respository = MovieRepository("05a6e212c37a1a4675f08109901adefc")


    private val _upcomingMovies = MutableLiveData<List<Movies>>()
    val upcomingMovies : LiveData<List<Movies>> = _upcomingMovies

    private val _popularMovies = MutableLiveData<List<Movies>>()
    val popularMovies: LiveData<List<Movies>> = _popularMovies

    private val _topRatedMovies = MutableLiveData<List<Movies>>()
    val topRatedMovies: LiveData<List<Movies>> = _topRatedMovies

    private val _nowPlayingMovies = MutableLiveData<List<Movies>>()
    val nowPlayingMovies: LiveData<List<Movies>> = _nowPlayingMovies

    //Funciones


    fun fetchUpComingMovies(){
        respository.getUpcomingMovies { response ->
            response?.let {
                _upcomingMovies.postValue(it.results)
            }
        }
    }

    fun fetchPopularMovies(){
        respository.getPopularMovies { respose ->
            respose?.let {
                _popularMovies.postValue(it.results)
            }
        }
    }
    fun fetchTopRatedMovies() {
        respository.getTopRatedMovies { response ->
            response?.let {
                _topRatedMovies.postValue(it.results)
            }
        }
    }

    fun fetchNowPlayingMovies() {
        respository.getNowPlayingMovies { response ->
            response?.let {
                _nowPlayingMovies.postValue(it.results)
            }
        }
    }

}