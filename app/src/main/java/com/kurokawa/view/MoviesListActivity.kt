package com.kurokawa.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.kurokawa.application.MyApplication
import com.kurokawa.data.remote.retrofit.RetrofitClient
import com.kurokawa.data.remote.service.MovieApiService
import com.kurokawa.data.repository.MovieListRepository
import com.kurokawa.data.room.adapter.MoviesListAdapter
import com.kurokawa.data.room.entities.MovieEntity
import com.kurokawa.databinding.ActivityMoviesListBinding
import com.kurokawa.model.MovieModel
import com.kurokawa.viewModel.MovieListViewModel
import java.util.ArrayList

class MoviesListActivity : AppCompatActivity() {
    /**VARIABLES DECLARADAS-----------------------------------------------------------------------*/
    private lateinit var binding: ActivityMoviesListBinding
    private lateinit var movieViewModel: MovieListViewModel
    private lateinit var adapter : MoviesListAdapter
    private lateinit var applicacion: MyApplication
    private lateinit var repository: MovieListRepository
    private lateinit var apiService: MovieApiService

    /**MAIN---------------------------------------------------------------------------------------*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**INICIALIZACION DE ADAPTER */
        val listMovies = mutableListOf<MovieEntity>()
        adapter = MoviesListAdapter(listMovies){ movies ->
            onItemSelected(movies)
        }

        /**INICIALIZACION DE RECYCLER */
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = adapter

        /**INICIALIZACION DE APPLICACION(DAO)*/
        applicacion = application as MyApplication

        /**INICIALIZACION DE API, REPOSITORY Y VIEWMODEL*/
        apiService = RetrofitClient.apiService
        repository = MovieListRepository(apiService,applicacion)
        movieViewModel = MovieListViewModel(repository)


        /**FUNCION PARA CONTROLAR LLAMADAS A LA API Y CARGAR TODAS LAS MOVIES*/
        loadMoviesApi()

        binding.btnFavoriteActivity.setOnClickListener {
            navigateToFAvorite()
        }

    }



    /**FUNCIONES ---------------------------------------------------------------------------------*/
    /**SI OBTIENE LISTAS VACIAS DE LA API LLAMA DE NUEVO SINO CARGA LAS MOVIES DESDE ROOM*/
    /**CARGA TODAS LAS MOVIES DE ROOM Y LAS ACTUALIZA CON EL ADAPTER*/
    private fun loadAllMoviesRoom() {
        movieViewModel.getAllMoviesRoom()
        movieViewModel.moviesFromRoom.observe(this) { localMovies ->
            if (localMovies.isEmpty()) {
                Log.e("MOVIES-LIST-ACTIVITY", "Las listas de ROOM estan vacias")
                loadMoviesApi()
            } else {
                adapter.submitList(localMovies)
                Log.e("MOVIES-LIST-ACTIVITY", "Las de ROOM sin movies duplicadas por categorias son: :${localMovies.size}")
            }
        }

    }

    /**CARGA TODAS LAS CATEGORIAS DE MOVIES DESDE LA API  Y LAS CONCATENA EN UNA SOLA LISTA */
    private fun loadMoviesApi() {
        movieViewModel.loadAllMovies()

        val liveDataList = listOf(
            movieViewModel.popularMovie,
            movieViewModel.topRatedMovie,
            movieViewModel.nowPlayingMovie,
            movieViewModel.upcomingMovie
        )
        val moviesResults = mutableListOf<List<MovieModel>?>()
        liveDataList.forEach { liveData ->
            liveData.observe(this) { moviesApi ->
                if (!moviesApi.isNullOrEmpty()) {
                      moviesResults.add(moviesApi)
                    if (moviesResults.size == liveDataList.size) {
                        Log.d("MOVIE-LIST-ACTIVITY", "Datos API recibidos, actualizando Room...")
                        loadAllMoviesRoom() // Actualizar UI con datos combinados
                    }
                }
            }
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


    private fun navigateToFAvorite() {

        val intent = Intent(this, FavoriteMovieActivity::class.java)
        startActivity(intent)
        Log.e("MOVIES-LIST-ACTIVITY", "Navegando a la movie activity ")

    }



}
