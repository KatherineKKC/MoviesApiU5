package com.kurokawa.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurokawa.application.MyApplication
import com.kurokawa.data.room.entities.Movies
import com.kurokawa.repository.MovieListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListViewModel : ViewModel() {

    private val _movieListResult = MutableLiveData<List<Movies>>()
    val movieListResult: LiveData<List<Movies>> get() = _movieListResult

    // Repositorio
    private val repository: MovieListRepository = MovieListRepository(
        (MyApplication.instance).movieDatabaseRoom.movieDao()
    )

    init {
        // Cargar películas automáticamente cuando se crea el ViewModel
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val count = repository.getMovieCount()
                if (count != 0) {
                    repository.getMovies().observeForever { movies ->
                        _movieListResult.postValue(movies)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
