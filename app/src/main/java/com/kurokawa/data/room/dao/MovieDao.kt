package com.kurokawa.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kurokawa.data.room.entities.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE category = :category")
    fun getMoviesByCategory(category: String): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE isFavoriteMovie = 1")
    fun getAllFavoritesMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE idMovie = :id LIMIT 1")
    fun getMovieById(id: Long): LiveData<MovieEntity>

    @Query("UPDATE movies SET isFavoriteMovie = :isFavorite WHERE idMovie = :id")
    suspend fun updateFavoriteStatus(id: Long, isFavorite: Boolean)

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()
}
