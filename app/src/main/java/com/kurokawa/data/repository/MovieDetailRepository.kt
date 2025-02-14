package com.kurokawa.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.kurokawa.application.MyApplication
import com.kurokawa.data.room.dao.MovieDao
import com.kurokawa.data.room.entities.MovieEntity

class MovieDetailRepository(private val applicacion :MyApplication){
    suspend fun updateFavoriteMovie(movieEntity: MovieEntity) {
        val category = movieEntity.category
        var isFavorite = movieEntity.isFavoriteMovie
        val idFavorite = movieEntity.idMovie
        applicacion.myDataBase.movieDao().updateFavoriteStatus(idFavorite,category,isFavorite)
        Log.e("MOVIE-DETAILS-REPOSITORY", "El estado de IDfAVORITO es: $idFavorite")

    }

    fun getMovieById(id: Long): LiveData<MovieEntity> {
        return applicacion.myDataBase.movieDao().getMovieById(id)
    }
}
