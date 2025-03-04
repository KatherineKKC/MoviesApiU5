package com.kurokawa.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kurokawa.data.repository.MovieDetailRepository
import com.kurokawa.data.room.entities.MovieEntity

class MovieDetailsViewModel(private val repository: MovieDetailRepository) : ViewModel() {

    /**FUNCION PARA OBTENER LA PELICULA MEDIANTE EL ID*/
    fun getMovieById(id: Long): LiveData<MovieEntity> {
        return repository.getMovieById(id)
    }

}






