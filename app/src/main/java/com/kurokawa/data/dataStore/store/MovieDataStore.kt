package com.kurokawa.data.dataStore.store

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kurokawa.data.dataStore.entities.MovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "movie_data_store")

class MovieDataStore(private val context: Context) {
    companion object {
        private val MOVIES_KEY = stringPreferencesKey("movies")
    }

    private val gson = Gson()

    /** 🔹 Guardar lista de películas */
    suspend fun insertMovies(movies: List<MovieEntity>) {
        val existingMovies = getAllMovies().firstOrNull() ?: emptyList() // 🔹 Obtener películas actuales
        val newMovies = (existingMovies + movies).distinctBy { it.idMovie } // 🔹 Evita duplicados

        val jsonMovies = gson.toJson(newMovies)
        context.dataStore.edit { preferences ->
            preferences[MOVIES_KEY] = jsonMovies
        }

        Log.e("MOVIE-DATASTORE", "Se han guardado ${newMovies.size} películas en DataStore")
    }

    /** 🔹 Obtener todas las películas como `Flow` */
    fun getAllMovies(): Flow<List<MovieEntity>> {
        return context.dataStore.data.map { preferences ->
            val jsonMovies = preferences[MOVIES_KEY] ?: "[]"
            val type = object : TypeToken<List<MovieEntity>>() {}.type
            gson.fromJson<List<MovieEntity>>(jsonMovies, type)
        }
    }
    fun getAllFavoritesMovies(): Flow<List<MovieEntity>> {
            return context.dataStore.data.map { preferences ->
                val jsonMovies = preferences[MOVIES_KEY] ?: "[]"
                val type = object : TypeToken<List<MovieEntity>>() {}.type
                val movies: List<MovieEntity> = gson.fromJson(jsonMovies, type)
                movies.filter { it.isFavoriteMovie}
            }
    }


    fun getMoviesByCategory(category: String): Flow<List<MovieEntity>> {
        return getAllMovies().map { movies ->
            Log.e("MOVIE-DATASTORE", "Total de películas antes de filtrar: ${movies.size}")
            val filteredMovies = movies.filter { it.category == category }
            Log.e("MOVIE-DATASTORE", "Películas filtradas en categoría '$category': ${filteredMovies.size}")
            filteredMovies
        }
    }



    /** 🔹 Obtener una película por su ID usando `Flow` */
    fun getMovieById(id: Long): Flow<MovieEntity?> {
        return getAllMovies().map { movies ->
            movies.find { it.idMovie == id }
        }
    }

    /** 🔹 Actualizar estado de favorito de una película en DataStore */
    suspend fun updateFavoriteStatus(id: Long, isFavorite: Boolean) {
        context.dataStore.edit { preferences ->
            // Obtener la lista actual de películas en formato JSON
            val jsonMovies = preferences[MOVIES_KEY] ?: "[]"
            val type = object : TypeToken<List<MovieEntity>>() {}.type
            val movies = gson.fromJson<List<MovieEntity>>(jsonMovies, type).toMutableList()

            // Buscar la película y actualizar el estado de favorito
            val index = movies.indexOfFirst { it.idMovie == id }
            if (index != -1) {
                movies[index] = movies[index].copy(isFavoriteMovie = isFavorite)
                preferences[MOVIES_KEY] = gson.toJson(movies) // ✅ Guardar solo la lista modificada en DataStore
                Log.e("MOVIE-DATASTORE", "✅ Favorito actualizado: ${movies[index].title} -> $isFavorite")
            } else {
                Log.e("MOVIE-DATASTORE", "⚠ No se encontró la película con ID $id para actualizar favoritos")
            }
        }
    }



}
