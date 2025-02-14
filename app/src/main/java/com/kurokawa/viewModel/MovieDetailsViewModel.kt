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

    fun updateFavoriteMovies(movieSelected: MovieEntity){

        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavoriteMovie(movieSelected)
            Log.e("MOVIE-DETAILS-ViEW-MODEL", "El estado de favorito es: ${movieSelected.isFavoriteMovie}")
        }

    }







}