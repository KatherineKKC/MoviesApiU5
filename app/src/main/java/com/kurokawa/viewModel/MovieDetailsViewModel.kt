package com.kurokawa.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurokawa.data.dataStore.entities.MovieEntity
import com.kurokawa.repository.MovieDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repository: MovieDetailRepository): ViewModel() {


    /**FUNCIONES----------------------------------------------------------------------------------*/
    fun stateFavoriteMovies(movie: MovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val newFavoriteState = !movie.isFavoriteMovie
            repository.stateFavoriteMovie(movie.idMovie, newFavoriteState)
            repository.getAllFavoriteMovies()
        }

    }

    fun getMovieById(id: Long): Flow<MovieEntity?> {
        return repository.getMovieById(id)
    }

}






