package com.kurokawa.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.kurokawa.data.network.RetrofitClient.apiService
import com.kurokawa.data.room.database.MovieDatabase
import com.kurokawa.data.room.entities.Movies
import com.kurokawa.utils.Resource

class MovieRepository(context: Context) {

    private val movieDao = MovieDatabase.getDatabase(context).movieDao()

    suspend fun fetchAndSaveMovies(category: MovieCategory, page: Int): Resource<List<Movies>> {
        return try {
            val response = when (category) {
                MovieCategory.POPULAR -> apiService.getPopularMovies(page = page)
                MovieCategory.TOP_RATED -> apiService.getTopRatedMovies(page = page)
                MovieCategory.UPCOMING -> apiService.getUpcomingMovies(page = page)
                MovieCategory.NOW_PLAYING -> apiService.getNowPlayingMovies(page = page)
            }

            if (response.isSuccessful) {
                val apiMovies = response.body()?.results ?: emptyList()
                val movies = apiMovies.map { apiMovie ->
                    Movies(
                        idMovie = apiMovie.idMovie,
                        title = apiMovie.title,
                        overview = apiMovie.overview,
                        posterPath = apiMovie.posterPath,
                        releaseDate = apiMovie.releaseDate,
                        originalTitle = apiMovie.originalTitle,
                        voteAverage = apiMovie.voteAverage,
                        isFavoriteMovie = false,
                        category = category.name
                    )
                }
                movieDao.insertMovies(movies)
                Resource.Success(movies)  // ✅ Devuelve la lista de películas
            } else {
                Resource.Error("Error ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Error de red: ${e.localizedMessage}")
        }
    }

    fun getLocalMoviesByCategory(category: MovieCategory): LiveData<List<Movies>> {
        return movieDao.getMoviesByCategory(category.name)
    }
}

enum class MovieCategory {
    POPULAR,
    TOP_RATED,
    UPCOMING,
    NOW_PLAYING
}
