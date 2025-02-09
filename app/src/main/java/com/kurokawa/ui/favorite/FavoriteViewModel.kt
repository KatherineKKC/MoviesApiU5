package com.kurokawa.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kurokawa.data.room.MovieDatabase
import com.kurokawa.data.room.model.Movies

class FavoriteViewModel :ViewModel(){
    private val movieDao = MovieDatabase.getInstance().movieDao()
    val favoriteMovies: LiveData<List<Movies>> = movieDao.getFavoriteMovies()

}