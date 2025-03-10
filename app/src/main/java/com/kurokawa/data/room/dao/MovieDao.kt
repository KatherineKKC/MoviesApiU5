package com.kurokawa.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kurokawa.data.room.entities.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieEntity>


    @Query("SELECT * FROM movies WHERE idMovie = :id")
    fun getMovieById(id: Long): LiveData<MovieEntity>


    @Query("SELECT * FROM movies WHERE category = :category")
    suspend fun getMoviesByCategory(category: String): List<MovieEntity>


    @Query("UPDATE movies SET isFavoriteMovie = :isFavorite WHERE idMovie = :idMovie")
    suspend fun updateFavoriteStatus(idMovie: Long, isFavorite: Boolean)


    @Query("SELECT * FROM movies WHERE isFavoriteMovie= 1")
    suspend fun getAllFavoritesMovies(): List<MovieEntity>


}