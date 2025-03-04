package com.kurokawa.data.sharedPreferences.storage

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.google.gson.Gson
import com.kurokawa.data.sharedPreferences.entities.MovieEntity

class SharedPreferencesStorageMovies(context: Context) {

    /**VARIABLES ---------------------------------------------------------------------------------*/
    private val sharedPreferences = context.getSharedPreferences("movies", Context.MODE_PRIVATE)

    //OBSERVADORES
    private val _allMovies = MutableLiveData<List<MovieEntity>>(emptyList())
    val allMovies: LiveData<List<MovieEntity>> get() = _allMovies

    //CARGAR PELICULAS
    init {
        loadMoviesFromPreferences() // Cargar películas al iniciar
    }


    /**FUNCIONES ---------------------------------------------------------------------------------*/
    // GUARDAR PELICULAS EN SHARED
    fun saveMovies(movies: List<MovieEntity>) {
        val currentMovies = _allMovies.value ?: emptyList()
        val newMovies = movies.filter { newMovie ->
            currentMovies.none { it.idMovie == newMovie.idMovie } // 🔹 Evita duplicados
        }

        if (newMovies.isNotEmpty()) {
            val updatedList = currentMovies + newMovies
            val json =
                Gson().toJson(updatedList) // 🔹 Guarda todas las películas sin eliminar las anteriores
            sharedPreferences.edit().putString("movies_list", json).apply()
            _allMovies.postValue(updatedList)
        }
    }


    //CARGAR LAS PELICULAS DESDE SHARED
    fun loadMoviesFromPreferences() {
        val json = sharedPreferences.getString("movies_list", null)
        val movies = if (json != null) {
            Gson().fromJson(json, Array<MovieEntity>::class.java).toList()
        } else {
            emptyList()
        }
        _allMovies.postValue(movies) // 🔹 Actualiza LiveData
    }


    //OBTENER LAS PELICULAS POR CATEGORIAS
    fun getMoviesByCategory(category: String): List<MovieEntity> {
        return _allMovies.value?.filter { it.category == category } ?: emptyList()
    }


    //OBTENER LAS PELICULAS POR ID
    fun getMovieById(idMovie: Long): MovieEntity? {
        return allMovies.value?.find { it.idMovie == idMovie }
    }

    // OBTENER LAS PELICULAS FAVORITAS
    fun getAllFavoriteMovies(): LiveData<List<MovieEntity>> {
        return _allMovies.map { movies ->
            movies.filter { it.isFavoriteMovie }
        }
    }

    //ACTUALIZA EL ESTADO FAVORITO DE LA PELICULA
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
