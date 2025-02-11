package com.kurokawa.repository

import androidx.lifecycle.LiveData
import com.kurokawa.application.MyApplication
import com.kurokawa.data.room.dao.MovieDao
import com.kurokawa.data.room.dao.UserDao
import com.kurokawa.data.room.entities.Movies

class MovieDetailRepository(private val movieDao: MovieDao){
    private lateinit var application: MyApplication


    suspend  fun getMovieDetail(movieId: Int): Movies{
       var movie = application.movieDatabaseRoom.movieDao().getMovieDetailById(movieId)
        return movie
    }

     suspend fun updateFavoriteStatus(movie: Movies) {
         application.movieDatabaseRoom.movieDao().updateMovie(movie)
     }


}
