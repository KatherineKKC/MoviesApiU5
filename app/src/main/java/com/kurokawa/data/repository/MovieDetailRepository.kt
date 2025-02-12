package com.kurokawa.data.repository

import com.kurokawa.application.MyApplication
import com.kurokawa.data.room.dao.MovieDao
import com.kurokawa.data.room.entities.MovieEntity

class MovieDetailRepository(private val movieDao: MovieDao){
    private lateinit var application: MyApplication


    suspend  fun getMovieDetail(movieId: Int): MovieEntity{
       var movie = application.movieDatabaseRoom.movieDao().getMovieDetailById(movieId)
        return movie
    }

     suspend fun updateFavoriteStatus(movie: MovieEntity) {
         application.movieDatabaseRoom.movieDao().updateMovie(movie)
     }


}
