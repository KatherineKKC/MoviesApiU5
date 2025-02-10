package com.kurokawa.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurokawa.data.room.database.MovieDatabase
import com.kurokawa.data.room.entities.Movies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val movieDao: MovieDatabase) : ViewModel() {
    private val _movie = MutableLiveData<Movies>()
    val movie: LiveData<Movies> get() = _movie

    fun getMovieById(idMovieSelected: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val movieDetails = movieDao.movieDao().getMovieById(idMovieSelected)
            _movie.postValue(movieDetails)
        }
    }



    fun toggleFavorite(movie: Movies) {
        movie.isFavoriteMovie = !movie.isFavoriteMovie
    }

    fun updateFavoriteStatus(movie: Movies) {
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.movieDao().updateMovie(movie)
        }
    }
}
