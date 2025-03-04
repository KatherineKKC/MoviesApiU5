package com.kurokawa.repository

import android.util.Log
import com.kurokawa.data.dataStore.entities.MovieEntity
import com.kurokawa.data.dataStore.store.MovieDataStore
import com.kurokawa.data.remote.service.MovieApiService
import com.kurokawa.model.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull


class MovieListRepository(
    private val apiKey: String,
    private val apiService: MovieApiService,
    private val movieDataStore: MovieDataStore
) {

    /**FUNCIONES PARA OBTENER LAS MOVIES DESDE LA API-----------------------------------------*/
    suspend fun syncMoviesWithDataStore(
        movies: List<MovieModel>,
        category: String
    ): List<MovieEntity> {
        val existingMovies = movieDataStore.getAllMovies().firstOrNull() ?: emptyList()
        val newMovies = movies.map { movieModel ->
            val existingMovie = existingMovies.find { it.idMovie == movieModel.id }
            MovieEntity(
                idMovie = movieModel.id,
                title = movieModel.title,
                posterPath = movieModel.posterPath,
                originalTitle = movieModel.originalTitle,
                overview = movieModel.overview,
                releaseDate = movieModel.releaseDate,
                voteAverage = movieModel.voteAverage,
                isFavoriteMovie = existingMovie?.isFavoriteMovie
                    ?: false, // Mantener el estado de favorito
                category = category

            )

        }

        Log.e("MOVIE-REPOSITORY", "Se están guardando ${newMovies.size} películas en DataStore")
        newMovies.forEach {
            Log.e(
                "MOVIE-REPOSITORY",
                "Guardada: ${it.title} - Categoría: ${it.category}"
            )
        }

        movieDataStore.insertMovies(newMovies)
        return newMovies
    }


    // Función genérica para obtener películas de la API
    private suspend fun getMoviesFromApi(
        apiCall: suspend () -> List<MovieModel>?,
        category: String
    ): List<MovieModel>? {
        return try {
            val movies = apiCall()
            movies?.let { syncMoviesWithDataStore(it, category) }
            movies
        } catch (e: Exception) {
            Log.e("MOVIE-REPOSITORY", "Error al obtener películas: ${e.message}")
            null
        }
    }

    // Funciones específicas para cada categoría
    suspend fun getPopularMovie(page: Int): List<MovieModel>? {
        return getMoviesFromApi(
            { apiService.getPopularMovies(apiKey, page).body()?.results },
            "Popular"
        )
    }

    suspend fun getTopRatedMovie(page: Int): List<MovieModel>? {
        return getMoviesFromApi(
            { apiService.getTopRatedMovies(apiKey, page).body()?.results },
            "TopRated"
        )
    }

    suspend fun getNowPlayingMovie(page: Int): List<MovieModel>? {
        return getMoviesFromApi(
            { apiService.getNowPlayingMovies(apiKey, page).body()?.results },
            "NowPlaying"
        )
    }

    suspend fun getUpcomingMovie(page: Int): List<MovieModel>? {
        return getMoviesFromApi(
            { apiService.getUpcomingMovies(apiKey, page).body()?.results },
            "Upcoming"
        )
    }

    // Resto del código sin cambios...


    /**FUNCIONES PARA OBTENER LAS MOVIES DESDE ROOM-----------------------------------------------*/
    /**OBTENER POR CATEGORIAS */
    fun getByCategory(category: String) = movieDataStore.getMoviesByCategory(category)

    /**OBTENER TODAS LAS PELICULAS */
    fun getAllMoviesDataStore(): Flow<List<MovieEntity>> = movieDataStore.getAllMovies()


    /**OBTENER TODAS LAS FAVORITAS */
    fun getAllFavoriteMovies(): Flow<List<MovieEntity>> = movieDataStore.getAllFavoritesMovies()


}





