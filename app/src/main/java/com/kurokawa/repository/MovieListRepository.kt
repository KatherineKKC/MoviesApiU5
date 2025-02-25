package com.kurokawa.repository

import android.util.Log
import com.kurokawa.data.remote.service.MovieApiService
import com.kurokawa.data.sharedPreferences.entities.MovieEntity
import com.kurokawa.data.sharedPreferences.storage.SharedPreferencesStorageMovies
import com.kurokawa.model.MovieModel


class MovieListRepository( private val apiService: MovieApiService, private val sharedStorageMovies: SharedPreferencesStorageMovies) {

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
             sharedStorageMovies.saveMovies(movieListEntity)
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
            sharedStorageMovies.saveMovies(movieListEntity)
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
            sharedStorageMovies.saveMovies(movieListEntity)
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
            sharedStorageMovies.saveMovies(movieListEntity)
                showMessageSuccessfulConsole(movieListEntity)
            }

            return moviesModel
        }
        showErrorToConsole()
        return   null
    }

    /**FUNCIONES PARA OBTENER LAS MOVIES DESDE ROOM-----------------------------------------------*/
    /**OBTENER POR CATEGORIAS */
    fun getByCategory(category: String)= sharedStorageMovies.getMoviesByCategory(category)

    /**OBTENER TODAS LAS PELICULAS */
    fun getAllMoviesSharedPreferenceStorage() :List<MovieEntity> =sharedStorageMovies.getAllMovies()


    /**OBTENER TODAS LAS FAVORITAS */
    fun getAllFavoriteMovies(): List<MovieEntity> = sharedStorageMovies.getAllFavoriteMovies()



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