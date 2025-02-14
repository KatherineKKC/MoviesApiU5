package com.kurokawa.data.repository

import android.util.Log
import com.bumptech.glide.Glide
import com.kurokawa.R
import com.kurokawa.application.MyApplication
import com.kurokawa.data.remote.service.MovieApiService
import com.kurokawa.data.room.entities.MovieEntity
import com.kurokawa.model.MovieModel

class MovieListRepository(private val apiService: MovieApiService, private val applicacion: MyApplication) {

    /**FUNCIONES PARA OBTENER RESULT DE APY/ INSERTAR EN ROOM /REGRESAR LA LISTA DE MOVIES(MODEL)-*/
    /**POPULAR*/
    suspend fun getPopularMovie(apiKey: String, page: Int): List<MovieModel>? {
        val response = apiService.getPopularMovies(apiKey,page)
        if (response.isSuccessful) {
            val moviesModel = response.body()?.results
            moviesModel?.let { listMovies ->
                val movieListEntity = listMovies.map { movieModel ->
                    MovieEntity(
                        idMovie = movieModel.id,
                        title = movieModel.title,
                        posterPath = movieModel.posterPath,
                        originalTitle = movieModel.originalTitle,
                        overview = movieModel.overview,
                        releaseDate = movieModel.releaseDate,
                        voteAverage = movieModel.voteAverage,
                        isFavoriteMovie = false,
                        category = "Popular",
                    )
                }
                applicacion.movieDatabaseRoom.movieDao().insertMovies(movieListEntity)
                showMessageSuccessfulConsole(movieListEntity)

            }
        }else{
            showErrorToConsole()
        }
        return if( response.isSuccessful) response.body()?.results else null
    }


    /**MEJOR VALORADAS TOP-RATED */
    suspend fun getTopRatedMovie(apiKey: String, page: Int): List<MovieModel>? {
        val response = apiService.getTopRatedMovies(apiKey,page)
        if (response.isSuccessful) {
            val moviesModel = response.body()?.results
            moviesModel?.let { listMovies ->
                val movieListEntity = listMovies.map { movieModel ->
                    MovieEntity(
                        idMovie =movieModel.id,
                        title = movieModel.title,
                        posterPath = movieModel.posterPath,
                        originalTitle = movieModel.originalTitle,
                        overview = movieModel.overview,
                        releaseDate = movieModel.releaseDate,
                        voteAverage = movieModel.voteAverage,
                        isFavoriteMovie = false,
                        category = "TopRated",
                    )
                }
                applicacion.movieDatabaseRoom.movieDao().insertMovies(movieListEntity)
                showMessageSuccessfulConsole(movieListEntity)

            }
        }else{
            showErrorToConsole()
        }
        return if( response.isSuccessful) response.body()?.results else null
    }


    /**EN CARTELERA NOW-PLAYING*/
    suspend fun getNowPlayingMovie(apiKey: String, page: Int): List<MovieModel>? {
        val response = apiService.getNowPlayingMovies(apiKey,page)
        if (response.isSuccessful) {
            val moviesModel = response.body()?.results
            moviesModel?.let { listMovies ->
                val movieListEntity = listMovies.map { movieModel ->
                    MovieEntity(
                        idMovie = movieModel.id,
                        title = movieModel.title,
                        posterPath = movieModel.posterPath,
                        originalTitle = movieModel.originalTitle,
                        overview = movieModel.overview,
                        releaseDate = movieModel.releaseDate,
                        voteAverage = movieModel.voteAverage,
                        isFavoriteMovie = false,
                        category = "NowPlaying",
                    )
                }
                applicacion.movieDatabaseRoom.movieDao().insertMovies(movieListEntity)
                showMessageSuccessfulConsole(movieListEntity)

            }
        }else{
            showErrorToConsole()
        }
        return if( response.isSuccessful) response.body()?.results else null
    }


    /**PROXIMOS ESTRENOS UPCOMING */
    suspend fun getUpcomingMovie(apiKey: String, page: Int): List<MovieModel>? {
        val response = apiService.getUpcomingMovies(apiKey,page)
        if (response.isSuccessful) {
            val moviesModel = response.body()?.results
            moviesModel?.let { listMovies ->
                val movieListEntity = listMovies.map { movieModel ->
                    MovieEntity(
                        idMovie = movieModel.id,
                        title = movieModel.title,
                        posterPath = movieModel.posterPath,
                        originalTitle = movieModel.originalTitle,
                        overview = movieModel.overview,
                        releaseDate = movieModel.releaseDate,
                        voteAverage = movieModel.voteAverage,
                        isFavoriteMovie = false,
                        category = "Upcoming",
                    )
                }
                applicacion.movieDatabaseRoom.movieDao().insertMovies(movieListEntity)
                showMessageSuccessfulConsole(movieListEntity)
            }

            return moviesModel
        }
        showErrorToConsole()
        return   null
    }

    /**FUNCIONES PARA OBTENER LAS MOVIES DESDE ROOM-----------------------------------------------*/
    /**OBTENER POR CATEGORIAS */
   suspend fun getByCategory(category: String)=applicacion.movieDatabaseRoom.movieDao().getMoviesByCategory(category)

    /**OBTENER TODAS LAS PELICULAS */
    suspend fun getAllMoviesRoom() = applicacion.movieDatabaseRoom.movieDao().getAllMovies()


    /**OBTENER TODAS LAS PELICULAS FAVORITAS */
    suspend fun getAllFavoriteMoviesRoom() = applicacion.movieDatabaseRoom.movieDao().getFavoriteMovies()



    /**FUNCIONES PARA MOSTRAR LAS PELICULAS Y ERRORES POR CONSOLA---------------------------------*/
    /**INSERT  MOVIES */
    fun showMessageSuccessfulConsole(listIntoRoom: List<MovieEntity>){
        Log.e("---MOVIE-LIST-REPOSITORY---", "se guardaron todas las peliculas en ROOM  ${listIntoRoom.size}")
    }

    /**ERROR INSERTAR MOVIES */
    fun showErrorToConsole(){
        Log.e("---MOVIE-LIST-REPOSITORY---","Error al INSERTAR las movies EN ROOM")
    }




}
