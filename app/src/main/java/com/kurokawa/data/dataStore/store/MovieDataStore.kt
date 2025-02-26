package com.kurokawa.data.dataStore.store


import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kurokawa.data.dataStore.entities.MovieEntity
import kotlinx.coroutines.runBlocking
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "movie_data_store")

class MovieDataStore(private val context: Context) {
    companion object {
        private val MOVIES_KEY = stringPreferencesKey("movies")
    }

    private val gson = Gson()

    /** 🔹 Guardar lista de películas */
    suspend fun insertMovies(movies: List<MovieEntity>) {
        val jsonMovies = gson.toJson(movies)
        context.dataStore.edit { preferences ->
            preferences[MOVIES_KEY] = jsonMovies
        }
    }

    /** 🔹 Obtener todas las películas (Convertir Flow a LiveData) */
    fun getAllMovies(): LiveData<List<MovieEntity>> = liveData {
        context.dataStore.data.map { preferences ->
            val jsonMovies = preferences[MOVIES_KEY] ?: "[]"
            val type = object : TypeToken<List<MovieEntity>>() {}.type
            gson.fromJson<List<MovieEntity>>(jsonMovies, type)
        }.collect { movies ->
            emit(movies) // 🔹 Emitimos la lista de películas para LiveData
        }
    }

    /** 🔹 Obtener películas por categoría */
    fun getMoviesByCategory(category: String): LiveData<List<MovieEntity>> {
        return getAllMovies().map { movies ->
            movies.filter { it.category == category }
        }
    }

    /** 🔹 Obtener películas favoritas */
    fun getAllFavoritesMovies(): LiveData<List<MovieEntity>> {
        return getAllMovies().map { movies ->
            movies.filter { it.isFavoriteMovie }
        }
    }

    /** 🔹 Obtener una película por su ID */
    fun getMovieById(id: Long): LiveData<MovieEntity?> {
        return getAllMovies().map { movies ->
            movies.find { it.idMovie == id }
        }
    }

    /** 🔹 Actualizar estado de favorito de una película */
    suspend fun updateFavoriteStatus(id: Long, isFavorite: Boolean) {
        val movies = runBlocking { getAllMovies().value ?: emptyList() }.toMutableList()
        val index = movies.indexOfFirst { it.idMovie == id }
        if (index != -1) {
            movies[index] = movies[index].copy(isFavoriteMovie = isFavorite)
            insertMovies(movies)
        }
    }
}

/**APUNTES*/

/**
private suspend fun saveDataInDataStore(
title :String, originTitle:String, posterPath:String, overview:String,
voteAverage:Double, relaseData :String , isFavorite:Boolean, category:String
) {
// Si estuvieramos en un Fragmento llamaríamos al contexto así:
// val context: Context = requireContext()
val context: Context = this
//Cuando accedamos a DataStore deberemos hacerlo en una corrutina
context.dataStore.edit { editor ->
editor[stringPreferencesKey("title")] = title
editor[booleanPreferencesKey("isFavorite")] = isFavorite
editor[StringPreferencesKey("originalTitle")] = originTitle ...
}
}*/