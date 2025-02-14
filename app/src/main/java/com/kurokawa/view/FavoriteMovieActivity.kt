package com.kurokawa.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.kurokawa.application.MyApplication
import com.kurokawa.data.room.adapter.MoviesListAdapter
import com.kurokawa.data.room.entities.MovieEntity
import com.kurokawa.databinding.ActivityFavoriteMovieBinding
import java.util.ArrayList

class FavoriteMovieActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityFavoriteMovieBinding
    private val binding: ActivityFavoriteMovieBinding get() = _binding

    private lateinit var adapter: MoviesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /**INICIALIZACION DE ADAPTER Y RECYCLER */
        val listFavoritesMovies = mutableListOf<MovieEntity>()
        adapter = MoviesListAdapter(listFavoritesMovies) {}

        binding.reciclerFavorite.layoutManager = GridLayoutManager(this, 2)
        binding.reciclerFavorite.adapter = adapter

        /**OBTENER Y MOSTRAR LAS PELICULAS FAVORITAS*/
        getFavoritesMovies()
    }

    private fun getFavoritesMovies() {
        val favoritesList: List<MovieEntity> = intent
            .getParcelableArrayListExtra<MovieEntity>("MOVIE-FAVORITES")
            ?: emptyList()

        if (favoritesList.isNotEmpty()) {
            adapter.submitList(favoritesList)
            Log.e("FAVORITE-MOVIE-ACTIVITY","Las peliculas favoritas obtenidas del intent son : ${favoritesList.size}")
        } else {
            Toast.makeText(this, "No tienes películas favoritas", Toast.LENGTH_SHORT).show()
        }
    }
}