package com.kurokawa.view.fragments

import com.kurokawa.data.sharedPreferences.entities.MovieEntity

interface FragmentMetodos {
    fun setupRecycler()
    fun observerFilter()
    fun getMovies()
    fun navigateToMovieDetail(movieDetail: MovieEntity)
}
