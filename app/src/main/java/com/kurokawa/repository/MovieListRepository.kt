package com.kurokawa.repository

import androidx.lifecycle.LiveData
import com.kurokawa.data.room.dao.MovieDao
import com.kurokawa.data.room.entities.Movies

class MovieListRepository(private val movieDao: MovieDao) {
    private val apiRepository = ApiRepository(movieDao)



    suspend fun getMovieCount(): Int {
        return movieDao.getMovieCount()
    }

   suspend fun getMovies(): LiveData<List<Movies>> {
        var moviesList = movieDao.getAllMovies()
        return moviesList
    }

    fun fetchMoviesFromApi() {
        apiRepository.getUpcomingMovies { response ->
            // Aquí puedes manejar la respuesta si es necesario
        }
        apiRepository.getPopularMovies { response ->
            // Aquí puedes manejar la respuesta si es necesario
        }
        apiRepository.getTopRatedMovies { response ->
            // Aquí puedes manejar la respuesta si es necesario
        }
        apiRepository.getNowPlayingMovies { response ->
            // Aquí puedes manejar la respuesta si es necesario
        }
    }
}