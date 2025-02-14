package com.kurokawa.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurokawa.data.repository.FavoriteRepository
import com.kurokawa.data.room.entities.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: FavoriteRepository): ViewModel(){

    private val _favorites = MutableLiveData<List<MovieEntity>>()
    val favorites: LiveData<List<MovieEntity>> = _favorites

    fun getAllFavoritesMoviesRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            val movies = repository.getAllFavoriteMovies()
            _favorites.postValue(movies)
            Log.e("MOVIE-LIST-VIEW-MODEL", "Películas favoritas obtenidas desde repository ${movies.size}")
        }
    }

    fun getMovieById(id: Long): LiveData<MovieEntity> {
        return repository.getMovieById(id)
    }

    fun updateFavoriteMovies(movieSelected: MovieEntity) {
        val idFavorite = movieSelected.idMovie
        val isFavorite = movieSelected.isFavoriteMovie
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavoriteMovie(idFavorite, isFavorite)
        }
    }
}