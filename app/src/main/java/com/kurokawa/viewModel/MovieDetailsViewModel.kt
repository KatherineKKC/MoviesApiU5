package com.kurokawa.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurokawa.data.room.entities.MovieEntity
import com.kurokawa.data.repository.MovieDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repository: MovieDetailRepository): ViewModel() {

    fun getMovieById(id: Long): LiveData<MovieEntity> {
        return repository.getMovieById(id)
    }

    fun updateFavoriteMovies(movieSelected: MovieEntity) {
            viewModelScope.launch(Dispatchers.IO) {
                val updatedMovie = movieSelected.copy(isFavoriteMovie = !movieSelected.isFavoriteMovie)
                repository.updateFavoriteMovie(updatedMovie) // ✅ Envía el nuevo estado
            }
        }

}






