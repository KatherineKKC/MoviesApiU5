package com.kurokawa.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.kurokawa.application.MyApplication
import com.kurokawa.data.room.adapter.MoviesListAdapter
import com.kurokawa.data.room.database.MovieDatabase
import com.kurokawa.data.room.entities.Movies
import com.kurokawa.repository.ApiRepository
import com.kurokawa.repository.MovieListRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListViewModel() : ViewModel(){
    private lateinit var applicacion : MyApplication

    private val _movieListResult = MutableLiveData<List<Movies>>()
    val movieListResult : LiveData<List<Movies>> get() = _movieListResult


    private lateinit var repository : MovieListRepository

    fun getAllMovies(){
        viewModelScope.launch {
            val moviesList = repository.getMovies()
            _movieListResult.postValue(moviesList)
        }
    }



}