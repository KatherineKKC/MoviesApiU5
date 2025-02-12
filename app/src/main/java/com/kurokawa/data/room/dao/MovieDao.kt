package com.kurokawa.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kurokawa.data.room.entities.Movies

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: Movies)

    // Insertar múltiples películas
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movies>)


    @Update
    suspend fun updateMovie(movies: Movies)

    @Delete
    suspend fun deleteMovie(movies: Movies)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<Movies>>

    @Query("SELECT * FROM movies WHERE isFavoriteMovie = 1")
    fun getFavoriteMovies(): LiveData<List<Movies>>

    @Query("SELECT * FROM movies WHERE idMovie = :movieId LIMIT 1")
    fun getMovieById(movieId: Int): LiveData<Movies>

    @Query("SELECT * FROM movies WHERE idMovie = :movieId LIMIT 1")
    fun getMovieDetailById(movieId: Int): Movies

    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getMovieCount(): Int

    @Query("SELECT * FROM movies WHERE category = :category")
    fun getMoviesByCategory(category: String): LiveData<List<Movies>>
}