package com.kurokawa.data.repository

import com.kurokawa.application.MyApplication
import com.kurokawa.data.room.dao.MovieDao
import com.kurokawa.data.room.entities.MovieEntity

class MovieDetailRepository(private val applicacion :MyApplication){
    suspend fun addFavoriteMovie(favorite: Boolean, movieFavorite: MovieEntity) {
        val category: String =movieFavorite.category
        //applicacion.movieDatabaseRoom.movieDao().updateFavoriteStatus( category,favorite)

    }


}
