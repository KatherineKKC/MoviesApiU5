package com.kurokawa.data.sharedPreferences.storage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kurokawa.data.sharedPreferences.entities.MovieEntity

class SharedPreferencesStorageMovies (private val context: Context) {
    // Nombre del almacenamiento de datos
    private val sharedStorageMovies= "SharedPreferencesDB"

    // Instancia de SharedPreferences
    private val sharedPreferencesStorage: SharedPreferences =
        context.getSharedPreferences(sharedStorageMovies, Context.MODE_PRIVATE)

    private val gson = Gson()

    // Método para guardar una lista de películas en SharedPreferences
    fun saveMovies(movies: List<MovieEntity>) {
        val editor = sharedPreferencesStorage.edit()
        val json = gson.toJson(movies)
        editor.putString("movie_list", json)
        editor.apply()
    }

    // Método para obtener todas las películas
    fun getAllMovies(): List<MovieEntity> {
        val json = sharedPreferencesStorage.getString("movie_list", null)
        return if (json != null) {
            val type = object : TypeToken<List<MovieEntity>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    // Método para obtener una película por su ID
    fun getMovieById(idMovie: Long): MovieEntity? {
        return getAllMovies().find { it.idMovie == idMovie }
    }

    // Método para obtener películas por categoría
    fun getMoviesByCategory(category: String): List<MovieEntity> {
        return getAllMovies().filter { it.category == category }
    }

    // Método para actualizar el estado de favorito de una película
    fun updateFavoriteStatus(idMovie: Long, isFavorite: Boolean) {
        val movies = getAllMovies().map { movie ->
            if (movie.idMovie == idMovie) movie.copy(isFavoriteMovie = isFavorite) else movie
        }
        saveMovies(movies)
    }

    // Método para obtener todas las películas marcadas como favoritas
    fun getAllFavoriteMovies(): List<MovieEntity> {
        return getAllMovies().filter { it.isFavoriteMovie }
    }

    // Método para eliminar una película por su ID
    fun deleteMovieById(idMovie: Long) {
        val movies = getAllMovies().filter { it.idMovie != idMovie }
        saveMovies(movies)
    }

    // Método para eliminar todas las películas
    fun clearAllMovies() {
        val editor = sharedPreferencesStorage.edit()
        editor.remove("movie_list")
        editor.apply()
    }
}
