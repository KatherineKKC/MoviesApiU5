package com.kurokawa.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kurokawa.data.room.entities.MovieEntity

@Dao
interface MovieDao {

    // 🔹 Insertar una lista de películas (reemplaza en caso de conflicto)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    // 🔹 Obtener todas las películas almacenadas en Room
    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<MovieEntity>>

    // 🔹 Obtener películas por categoría
    @Query("SELECT * FROM movies WHERE category = :category")
    fun getMoviesByCategory(category: String): LiveData<List<MovieEntity>>

    // 🔹 Obtener películas favoritas
    @Query("SELECT * FROM movies WHERE isFavoriteMovie = 1")
    fun getAllFavoritesMovies(): LiveData<List<MovieEntity>>

    // 🔹 Obtener una película por su ID
    @Query("SELECT * FROM movies WHERE idMovie = :id LIMIT 1")
    fun getMovieById(id: Long): LiveData<MovieEntity>

    // 🔹 Actualizar estado de favorito de una película
    @Query("UPDATE movies SET isFavoriteMovie = :isFavorite WHERE idMovie = :id")
    suspend fun updateFavoriteStatus(id: Long, isFavorite: Boolean)

    // 🔹 Eliminar todas las películas (opcional si necesitas limpiar la base de datos)
    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()
}
