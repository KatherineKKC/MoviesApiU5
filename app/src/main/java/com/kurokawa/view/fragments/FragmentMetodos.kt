package com.kurokawa.view.fragments

import com.kurokawa.data.room.entities.MovieEntity
interface FragmentMetodos {
    fun setupRecycler()
    fun observerFilter()
    fun getMovies()
    fun navigateToMovieDetail(movieDetail: MovieEntity)
}
