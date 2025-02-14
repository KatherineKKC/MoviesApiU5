package com.kurokawa.viewModel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurokawa.application.MyApplication
import com.kurokawa.data.room.entities.MovieEntity
import com.kurokawa.data.repository.MovieDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repository: MovieDetailRepository): ViewModel() {
    fun movieIsFavorite(isFavorite: Boolean, movieFavorite: MovieEntity) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addFavoriteMovie(isFavorite, movieFavorite)

        }

    }


}