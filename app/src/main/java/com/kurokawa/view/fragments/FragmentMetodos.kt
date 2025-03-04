package com.kurokawa.view.fragments

import com.kurokawa.data.paperDB.entities.MovieEntity
interface FragmentMetodos {
    //METODOS PARA IMPLEMENTAR EN LOS FRAGMENTOS
    fun setupRecycler()
    fun observerFilter()
    fun getMovies()
    fun navigateToMovieDetail(movieDetail: MovieEntity)
}
