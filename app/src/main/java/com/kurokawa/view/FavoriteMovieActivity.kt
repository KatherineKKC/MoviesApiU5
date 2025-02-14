package com.kurokawa.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.kurokawa.application.MyApplication
import com.kurokawa.viewModel.FavoriteViewModel
import com.kurokawa.data.room.adapter.MoviesListAdapter
import com.kurokawa.data.room.entities.MovieEntity
import com.kurokawa.databinding.ActivityFavoriteMovieBinding
import com.kurokawa.data.repository.FavoriteRepository

class FavoriteMovieActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityFavoriteMovieBinding
    private val binding: ActivityFavoriteMovieBinding get() = _binding
    
    private lateinit var applicacion:MyApplication
    private lateinit var repository: FavoriteRepository
    private lateinit var favoriteViewModel : FavoriteViewModel
    private lateinit var adapter: MoviesListAdapter
    private var currentMovie :MovieEntity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inicializer()


        /**INICIALIZACION DE ADAPTER */



        binding.reciclerFavorite.layoutManager = GridLayoutManager(this, 2)
        binding.reciclerFavorite.adapter = adapter

        /**OBTENER Y MOSTRAR LAS PELICULAS FAVORITAS*/

        setupFavorites()
    }



    /**FUNCIONES----------------------------------------------------------------------------------*/
    /**INICIA APP, REPO Y VIEWMODEL*/

    private fun setupRecycler(){
        val listMovies = mutableListOf<MovieEntity>()
        adapter = MoviesListAdapter(listMovies){ movies ->
            onItemSelected(movies)
            observerStateMovies(movies.idMovie)
        }
    }

    private fun inicializer(){
        applicacion = application as MyApplication
        repository = FavoriteRepository(applicacion)
        favoriteViewModel = FavoriteViewModel(repository)
    }

    private fun observerStateMovies(idMovie: Long) {
        favoriteViewModel.getMovieById(idMovie).observe(this){ updateMovie->
            currentMovie =updateMovie
            navigateToMovieDetail(updateMovie)
        }
    }

    /**OBTIENE TODAS LAS PELICULAS FAVORITAS Y OBSERVA LOS CAMBIOS */
    private fun setupFavorites() {
       favoriteViewModel.favorites.observe(this) { favoritesList ->
           val uniqueList = favoritesList.distinctBy { it.idMovie }
           adapter.submitList(uniqueList)
        }
    }


    private fun onItemSelected(movieSelected: MovieEntity) {
        if(movieSelected.idMovie != null){
            navigateToMovieDetail(movieSelected)
            Log.e("MOVIES-LIST-ACTIVITY", "La movie seleccionada es :$movieSelected ")
        }else{
            Log.e("MOVIES-LIST-ACTIVITY", "Error al obtener la movie seleccionada ")
        }
    }

    private fun navigateToMovieDetail(movieSelected: MovieEntity) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("MOVIE", movieSelected)
        startActivity(intent)
        Log.e("MOVIES-LIST-ACTIVITY", "La movie enviada a la activity detail es: $movieSelected ")

    }


}