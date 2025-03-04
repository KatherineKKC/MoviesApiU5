package com.kurokawa.repository

import android.util.Log
import com.kurokawa.data.dataStore.entities.MovieEntity
import com.kurokawa.data.dataStore.store.MovieDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieDetailRepository(private val movieDataStore: MovieDataStore) {
    /**FUNCIONES----------------------------------------------------------------------------------*/
    suspend fun stateFavoriteMovie(id: Long, isFavorite: Boolean) {
        movieDataStore.updateFavoriteStatus(id, isFavorite)
        Log.e("MOVIE-DETAILS-REPOSITORY", "El estado de favorito  es: $isFavorite")
    }

    fun getMovieById(id: Long): Flow<MovieEntity?> {
        return movieDataStore.getMovieById(id)
    }

    fun getAllMoviesDataStore(): Flow<List<MovieEntity>> = movieDataStore.getAllMovies()

    /**OBTENER TODAS LAS FAVORITAS */
    fun getAllFavoriteMovies(): Flow<List<MovieEntity>> {
        return movieDataStore.getAllMovies().map { movies ->
            val favoriteMovies = movies.filter { it.isFavoriteMovie == true }
            Log.e("MOVIE-DATASTORE", "Películas favoritas encontradas: ${favoriteMovies.size}")
            favoriteMovies
        }
    }

}
