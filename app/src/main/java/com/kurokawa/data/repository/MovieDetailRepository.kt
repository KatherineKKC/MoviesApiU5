package com.kurokawa.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.kurokawa.application.MyApplication
import com.kurokawa.data.room.dao.MovieDao
import com.kurokawa.data.room.entities.MovieEntity

class MovieDetailRepository(private val applicacion :MyApplication){
    suspend fun updateFavoriteMovie(movieEntity: MovieEntity) {
        var isFavorite = movieEntity.isFavoriteMovie
        val idFavorite = movieEntity.idMovie
        applicacion.myDataBase.movieDao().updateFavoriteStatus(idFavorite,isFavorite)
        Log.e("MOVIE-DETAILS-REPOSITORY", "El estado de favorito  es: $isFavorite")

    }

    fun getMovieById(id: Long): LiveData<MovieEntity> {
        return applicacion.myDataBase.movieDao().getMovieById(id)
    }

}
