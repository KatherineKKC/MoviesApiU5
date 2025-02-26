package com.kurokawa.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurokawa.data.paperDB.entities.MovieEntity
import com.kurokawa.repository.MovieDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repository: MovieDetailRepository): ViewModel() {

    /**FUNCIONES----------------------------------------------------------------------------------*/
    fun updateFavoriteMovies(movieSelected: MovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedMovie = movieSelected.copy(isFavoriteMovie = !movieSelected.isFavoriteMovie)
            repository.updateFavoriteMovie(updatedMovie) // ✅ Envía el nuevo estado
        }
    }

    fun getMovieById(id: Long): MovieEntity? {
        return repository.getMovieById(id)
    }

}






