package com.kurokawa.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.kurokawa.data.room.database.MyDataBase
import com.kurokawa.data.room.entities.MovieEntity

class MovieDetailRepository(private val myDataBase: MyDataBase){
    /**FUNCIONES----------------------------------------------------------------------------------*/
    suspend fun updateFavoriteMovie(movieEntity: MovieEntity) {
        var isFavorite = movieEntity.isFavoriteMovie
        val idFavorite = movieEntity.idMovie
       myDataBase.movieDao().updateFavoriteStatus(idFavorite,isFavorite)
        Log.e("MOVIE-DETAILS-REPOSITORY", "El estado de favorito  es: $isFavorite")

    }

    fun getMovieById(id: Long): LiveData<MovieEntity> {
        return myDataBase.movieDao().getMovieById(id)
    }

}
