package com.kurokawa.repository

import android.util.Log
import com.kurokawa.data.paperDB.entities.MovieEntity
import com.kurokawa.data.paperDB.paperDataBase.PaperDBMovies

class MovieDetailRepository(private val paperDBMovie: PaperDBMovies){
    /**FUNCIONES----------------------------------------------------------------------------------*/
   fun updateFavoriteMovie(movieEntity: MovieEntity) {
        var isFavorite = movieEntity.isFavoriteMovie
        val idFavorite = movieEntity.idMovie
       paperDBMovie.updateFavoriteStatus(idFavorite,isFavorite)
        Log.e("MOVIE-DETAILS-REPOSITORY", "El estado de favorito  es: $isFavorite")

    }

    fun getMovieById(id: Long): MovieEntity? {
        return paperDBMovie.getMovieById(id)
    }

}
