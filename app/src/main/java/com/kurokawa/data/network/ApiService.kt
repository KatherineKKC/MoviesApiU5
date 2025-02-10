package com.kurokawa.data.network

import com.kurokawa.data.room.entities.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("language") language: String = "es-ES",
        @Query("api_key")  apiKey:String
    ): Call<MovieResponse>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("language") language: String = "es-ES",
        @Query("api_key")  apiKey:String
    ): Call<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("language") language: String = "es-ES",
        @Query("api_key") apiKey: String
    ): Call<MovieResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("language") language: String = "es-ES",
        @Query("api_key") apiKey: String
    ): Call<MovieResponse>



}