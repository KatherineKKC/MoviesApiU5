package com.kurokawa.data.network

import com.kurokawa.data.room.entities.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "es-ES",
        @Query("page") page: Int
    ): Response<MovieResponse>  // Devuelve Response para manejar errores HTTP

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = "es-ES",
        @Query("page") page: Int
    ): Response<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String = "es-ES",
        @Query("page") page: Int
    ): Response<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "es-ES",
        @Query("page") page: Int
    ): Response<MovieResponse>
}
