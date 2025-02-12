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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: MovieEntity)

    // Insertar múltiples películas
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies:List<MovieEntity>)
    @Update
    suspend fun updateMovie(movies: MovieEntity)

    @Delete
    suspend fun deleteMovie(movies: MovieEntity)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE isFavoriteMovie = 1")
    fun getFavoriteMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE idMovie = :movieId LIMIT 1")
    fun getMovieById(movieId: Int): LiveData<MovieEntity>

    @Query("SELECT * FROM movies WHERE idMovie = :movieId LIMIT 1")
    fun getMovieDetailById(movieId: Int): MovieEntity

    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getMovieCount(): Int

    @Query("SELECT * FROM movies WHERE category = :category")
    fun getMoviesByCategory(category: String): LiveData<List<MovieEntity>>
}