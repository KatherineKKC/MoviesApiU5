package com.kurokawa.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.kurokawa.data.sharedPreferences.entities.MovieEntity
import com.kurokawa.data.sharedPreferences.storage.SharedPreferencesStorageMovies

class MovieDetailRepository(private val sharedStorageMovies: SharedPreferencesStorageMovies){
    /**FUNCIONES----------------------------------------------------------------------------------*/
   fun updateFavoriteMovie(movieEntity: MovieEntity) {
        var isFavorite = movieEntity.isFavoriteMovie
        val idFavorite = movieEntity.idMovie
       sharedStorageMovies.updateFavoriteStatus(idFavorite,isFavorite)
        Log.e("MOVIE-DETAILS-REPOSITORY", "El estado de favorito  es: $isFavorite")

    }

    fun getMovieById(id: Long): MovieEntity? {
        return sharedStorageMovies.getMovieById(id)
    }

}
