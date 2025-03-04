package com.kurokawa.data.repository

import androidx.lifecycle.LiveData
import com.kurokawa.application.MyApplication
import com.kurokawa.data.room.entities.MovieEntity

class MovieDetailRepository(private val applicacion: MyApplication) {

    //Obtenemos los datos de la movie seleccionada mediante el id
    fun getMovieById(id: Long): LiveData<MovieEntity> {
        return applicacion.myDataBase.movieDao().getMovieById(id)
    }

}
