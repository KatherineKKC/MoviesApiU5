package com.kurokawa.repository

import com.kurokawa.application.MyApplication
import com.kurokawa.data.room.entities.Movies

class MovieListRepository() {
    private lateinit var applicacion :MyApplication

    fun getMovies(): List<Movies> {
        var moviesList = applicacion.movieDatabaseRoom.movieDao().getAllMovies()
        return moviesList
    }
}