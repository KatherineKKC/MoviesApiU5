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
    suspend fun insertMovies(movies:List<MovieEntity>)

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE isFavoriteMovie = 1")
    suspend fun getFavoriteMovies():  List<MovieEntity>

    @Query("SELECT * FROM movies WHERE idMovie = :id")
    fun getMovieById(id: Long): LiveData<MovieEntity>


    @Query("SELECT * FROM movies WHERE category = :category")
    suspend fun getMoviesByCategory(category: String):  List<MovieEntity>



    @Query("UPDATE movies SET isFavoriteMovie = :isFavorite WHERE idMovie = :idMovie AND category = :category")
    suspend fun updateFavoriteStatus(idMovie: Long, category: String, isFavorite: Boolean)


    @Query("SELECT * FROM movies WHERE isFavoriteMovie= :isFavorite")
    suspend fun getAllFavoritesMovies (isFavorite: Boolean):List<MovieEntity>



}