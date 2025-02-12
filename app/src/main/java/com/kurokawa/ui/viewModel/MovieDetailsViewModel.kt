package com.kurokawa.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurokawa.application.MyApplication
import com.kurokawa.data.room.entities.Movies
import com.kurokawa.repository.LoginRepository
import com.kurokawa.repository.MovieDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(): ViewModel() {
    private var _movieDetailResult = MutableLiveData<Movies>()
    val movieDetailResponse : LiveData<Movies> get() = _movieDetailResult

    //REPOSITORIO
    private val repository: MovieDetailRepository = MovieDetailRepository(
        MyApplication.instance.movieDatabaseRoom.movieDao()
    )

    fun getMovieDetails(movieId :Int){
        var movieDetail :Movies
        viewModelScope.launch(Dispatchers.IO){
            movieDetail = repository.getMovieDetail(movieId)
            _movieDetailResult.postValue(movieDetail)
        }

    }

    fun toggleFavorite() {
        _movieDetailResult.value?.let { movie ->
            val updatedMovie = movie.copy(isFavoriteMovie = !movie.isFavoriteMovie)
            _movieDetailResult.postValue(updatedMovie)
            updateFavoriteInDatabase(updatedMovie)
        }
    }
    private fun updateFavoriteInDatabase(movie: Movies) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavoriteStatus(movie)
        }
    }


}