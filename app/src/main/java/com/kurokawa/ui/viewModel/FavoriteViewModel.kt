package com.kurokawa.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kurokawa.data.room.database.MovieDatabase
import com.kurokawa.data.room.entities.Movies

class FavoriteViewModel :ViewModel(){
    private val movieDao = MovieDatabase.getInstance().movieDao()
    val favoriteMovies: LiveData<List<Movies>> = movieDao.getFavoriteMovies()

}