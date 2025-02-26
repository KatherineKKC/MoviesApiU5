package com.kurokawa.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.kurokawa.data.dataStore.entities.MovieEntity
import com.kurokawa.data.dataStore.store.MovieDataStore

class MovieDetailRepository(private val movieDataStore: MovieDataStore){
    /**FUNCIONES----------------------------------------------------------------------------------*/
    suspend fun updateFavoriteMovie(movieEntity: MovieEntity) {
        var isFavorite = movieEntity.isFavoriteMovie
        val idFavorite = movieEntity.idMovie
      movieDataStore.updateFavoriteStatus(idFavorite,isFavorite)
        Log.e("MOVIE-DETAILS-REPOSITORY", "El estado de favorito  es: $isFavorite")

    }

    fun getMovieById(id: Long): LiveData<MovieEntity?> {
        return movieDataStore.getMovieById(id)
    }

}
