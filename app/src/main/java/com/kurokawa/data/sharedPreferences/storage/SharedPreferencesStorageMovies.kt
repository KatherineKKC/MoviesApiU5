package com.kurokawa.data.sharedPreferences.storage

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.google.gson.Gson
import com.kurokawa.data.sharedPreferences.entities.MovieEntity

class SharedPreferencesStorageMovies(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("movies", Context.MODE_PRIVATE)

    // 🔹 LiveData para todas las películas
    private val _allMovies = MutableLiveData<List<MovieEntity>>(emptyList())
    val allMovies: LiveData<List<MovieEntity>> get() = _allMovies



    init {
        loadMoviesFromPreferences() // Cargar películas al iniciar
    }

    // 🔹 Guardar películas en SharedPreferences y actualizar LiveData
    fun saveMovies(movies: List<MovieEntity>) {
        val currentMovies = _allMovies.value ?: emptyList()
        val newMovies = movies.filter { newMovie ->
            currentMovies.none { it.idMovie == newMovie.idMovie } // 🔹 Evita duplicados
        }

        if (newMovies.isNotEmpty()) {
            val updatedList = currentMovies + newMovies
            val json = Gson().toJson(updatedList) // 🔹 Guarda todas las películas sin eliminar las anteriores
            sharedPreferences.edit().putString("movies_list", json).apply()
            _allMovies.postValue(updatedList)
        }
    }


    // 🔹 Cargar todas las películas desde SharedPreferences
     fun loadMoviesFromPreferences() {
        val json = sharedPreferences.getString("movies_list", null)
        val movies = if (json != null) {
            Gson().fromJson(json, Array<MovieEntity>::class.java).toList()
        } else {
            emptyList()
        }
        _allMovies.postValue(movies) // 🔹 Actualiza LiveData
    }

    // 🔹 Obtener películas por categoría (usando `map {}`)
    fun getMoviesByCategory(category: String): List<MovieEntity> {
        return _allMovies.value?.filter { it.category == category } ?: emptyList()
    }


    // 🔹 Obtener una película por su ID (sin depender de `LiveData`)
    fun getMovieById(idMovie: Long): MovieEntity? {
        return allMovies.value?.find { it.idMovie == idMovie }
    }

    // 🔹 Obtener todas las películas favoritas (usando `map {}`)
    fun getAllFavoriteMovies(): LiveData<List<MovieEntity>> {
        return _allMovies.map { movies ->
            movies.filter { it.isFavoriteMovie }
        }
    }

    fun updateFavoriteStatus(idFavorite: Long, favorite: Boolean) {
        val updatedMovies = _allMovies.value?.map { movie ->
            if (movie.idMovie == idFavorite) {
                movie.copy(isFavoriteMovie = favorite)
            } else {
                movie
            }
        } ?: emptyList()

        // Guardar los cambios en SharedPreferences
        val json = Gson().toJson(updatedMovies)
        sharedPreferences.edit().putString("movies_list", json).apply()

        // Notificar a los observadores
        _allMovies.postValue(updatedMovies)
    }
}
