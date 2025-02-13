package com.kurokawa.data.repository

import com.kurokawa.application.MyApplication
import com.kurokawa.data.room.dao.MovieDao
import com.kurokawa.data.room.entities.MovieEntity

class MovieDetailRepository(private val applicacion :MyApplication){

    suspend  fun getMovieDetail(movieId: Int): MovieEntity{
       var movie = applicacion.movieDatabaseRoom.movieDao().getMovieById(movieId)
        return movie
    }

     suspend fun updateFavoriteStatus(movie: MovieEntity) {
         applicacion.movieDatabaseRoom.movieDao().updateMovie(movie)
     }


}
