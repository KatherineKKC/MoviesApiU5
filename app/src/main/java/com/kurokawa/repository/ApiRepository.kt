package com.kurokawa.repository

import android.util.Log
import com.kurokawa.data.network.RetrofitClient
import com.kurokawa.data.room.dao.MovieDao
import com.kurokawa.data.room.entities.MovieResponse
import com.kurokawa.data.room.entities.Movies
import com.kurokawa.utils.Constans
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiRepository(private val movieDao: MovieDao) {

    //Obtenemos la api de utils donde esta almacenada
    private val apiKey = Constans.API_KEY

    fun getUpcomingMovies(onResult: (MovieResponse?) -> Unit) {
        RetrofitClient.instance.getUpcomingMovies(apiKey = apiKey)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        val movieResponse = response.body()
                        movieResponse?.let {
                            val movieEntities = movieResponse.results.map { movie ->
                                Movies(
                                    idMovie = movie.idMovie,
                                    posterPath = movie.posterPath ?: "",
                                    title = movie.title,
                                    originalTitle = movie.originalTitle,
                                    overview = movie.overview,
                                    releaseDate = movie.releaseDate,
                                    voteAverage = movie.voteAverage,
                                    isFavoriteMovie = false,
                                    category = "upcoming"
                                )
                            }

                            // Insertar en la base de datos (usando Coroutine)
                            CoroutineScope(Dispatchers.IO).launch {
                                movieDao.insertMovies(movieEntities)
                                val count = movieDao.getMovieCount()
                                Log.d("Database", "Número de películas insertadas: $count")
                            }
                        }
                        onResult(movieResponse)
                    } else {
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    onResult(null)
                }
            })
    }


    fun getPopularMovies(onResult: (MovieResponse?) -> Unit) {
        RetrofitClient.instance.getPopularMovies(apiKey = apiKey)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    if (response.isSuccessful) {
                        val movieResponse = response.body()
                        movieResponse?.let {
                            val movieEntities = movieResponse.results.map { movie ->
                                Movies(
                                    idMovie = movie.idMovie,
                                    posterPath = movie.posterPath ?: "",
                                    title = movie.title,
                                    originalTitle = movie.originalTitle,
                                    overview = movie.overview,
                                    releaseDate = movie.releaseDate,
                                    voteAverage = movie.voteAverage,
                                    isFavoriteMovie = false,
                                    category = "upcoming"
                                )
                            }

                            // Insertar en la base de datos (usando Coroutine)
                            CoroutineScope(Dispatchers.IO).launch {
                                movieDao.insertMovies(movieEntities)
                            }
                        }
                        onResult(movieResponse)
                    } else {
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    onResult(null)
                }
            })
    }


    fun getTopRatedMovies(onResult: (MovieResponse?) -> Unit) {
        RetrofitClient.instance.getTopRatedMovies(apiKey = apiKey)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    if (response.isSuccessful){
                        val movieResponse = response.body()
                        movieResponse?.let {
                            val movieEntity = movieResponse.results.map { movie ->
                                Movies(
                                    idMovie = movie.idMovie,
                                    posterPath = movie.posterPath ?: "",
                                    title = movie.title,
                                    originalTitle = movie.originalTitle,
                                    overview = movie.overview,
                                    releaseDate = movie.releaseDate,
                                    voteAverage = movie.voteAverage,
                                    isFavoriteMovie = false,
                                    category = "upcoming"
                                )
                            }
                            CoroutineScope(Dispatchers.IO).launch{
                                movieDao.insertMovies(movieEntity)
                            }
                        }
                        onResult(movieResponse)
                    }else{
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    onResult(null)
                }
            })
    }

    fun getNowPlayingMovies(onResult: (MovieResponse?) -> Unit) {
        RetrofitClient.instance.getNowPlayingMovies(apiKey = apiKey)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    if (response.isSuccessful){
                        val movieResponse = response.body()
                        movieResponse?.let{
                            val movieEntities = movieResponse.results.map { movie ->
                                Movies(
                                    idMovie = movie.idMovie,
                                    posterPath = movie.posterPath ?: "",
                                    title = movie.title,
                                    originalTitle = movie.originalTitle,
                                    overview = movie.overview,
                                    releaseDate = movie.releaseDate,
                                    voteAverage = movie.voteAverage,
                                    isFavoriteMovie = false,
                                    category = "upcoming"
                                )
                            }
                            CoroutineScope(Dispatchers.IO).launch {
                                movieDao.insertMovies(movieEntities)
                            }
                        }
                        onResult(movieResponse)
                    }else{
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    onResult(null)
                }
            })
    }
}
