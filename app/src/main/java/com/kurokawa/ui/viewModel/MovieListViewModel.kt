package com.kurokawa.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurokawa.data.repository.MovieCategory
import com.kurokawa.data.repository.MovieRepository
import com.kurokawa.data.room.entities.Movies
import com.kurokawa.utils.Resource
import kotlinx.coroutines.launch

class MovieListViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _movies = MutableLiveData<Resource<List<Movies>>>()
    val movies: LiveData<Resource<List<Movies>>> get() = _movies

    fun fetchMovies(category: MovieCategory, page: Int) {
        viewModelScope.launch {
            _movies.value = Resource.Loading()
            val result = repository.fetchAndSaveMovies(category, page)
            _movies.value = result
        }
    }

    fun getLocalMovies(category: MovieCategory): LiveData<List<Movies>> {
        return repository.getLocalMoviesByCategory(category)
    }
}
