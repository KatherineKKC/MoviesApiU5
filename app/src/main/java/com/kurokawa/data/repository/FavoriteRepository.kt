package com.kurokawa.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.kurokawa.application.MyApplication
import com.kurokawa.data.room.entities.MovieEntity

class FavoriteRepository(private val applicacion:MyApplication) {


    suspend fun updateFavoriteMovie(idMovie: Long, isFavorite: Boolean) {
        applicacion.myDataBase.movieDao().updateFavoriteStatus(idMovie, isFavorite)
    }

    suspend fun getAllFavoriteMovies():List<MovieEntity>{
        return  applicacion.myDataBase.movieDao().getAllFavoritesMovies()
    }

    fun getMovieById(id: Long): LiveData<MovieEntity> {
        return applicacion.myDataBase.movieDao().getMovieById(id)

    }
}