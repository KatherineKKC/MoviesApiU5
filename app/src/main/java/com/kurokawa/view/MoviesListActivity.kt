package com.kurokawa.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.kurokawa.application.MyApplication
import com.kurokawa.data.remote.retrofit.RetrofitClient
import com.kurokawa.data.remote.service.MovieApiService
import com.kurokawa.data.repository.MovieDetailRepository
import com.kurokawa.data.repository.MovieListRepository
import com.kurokawa.data.room.adapter.MoviesListAdapter
import com.kurokawa.data.room.entities.MovieEntity
import com.kurokawa.databinding.ActivityMoviesListBinding
import com.kurokawa.model.MovieModel
import com.kurokawa.viewModel.MovieListViewModel

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
        validateList()

    }


    /**FUNCIONES ---------------------------------------------------------------------------------*/
    /**SI OBTIENE LISTAS VACIAS DE LA API LLAMA DE NUEVO SINO CARGA LAS MOVIES DESDE ROOM*/
    private fun validateList() {
        loadMoviesApi { moviesApiList ->
            if(moviesApiList.all { it.isNullOrEmpty() }){
                Log.d("MOVIES-LIST-ACTIVITY", "Las listas estaban vacías, reintentando cargar desde la API...")
                reLoadMoviesApi()
            }else{
                loadAllMoviesRoom()
            }
        }
    }


    /**REPITE LA LLAMADA A LA API / CARGA LAS MOVIES DESDE ROOM*/
    private fun reLoadMoviesApi() {
        loadMoviesApi { moviesApiList ->
            if (moviesApiList.all { it.isNullOrEmpty() }) {
                Log.e("MOVIES-LIST-ACTIVITY", "No se pudieron cargar películas desde la API después del reintento.")
            } else {
                loadAllMoviesRoom()
            }
        }
    }


    /**CARGA TODAS LAS MOVIES DE ROOM Y LAS ACTUALIZA CON EL ADAPTER*/
    private fun loadAllMoviesRoom() {
        movieViewModel.moviesFromRoom.observe(this) { movies ->
            if (movies.isNullOrEmpty()) {
                Log.e("MOVIES-LIST-ACTIVITY", "Las listas de ROOM estan vacias")
            } else {
                adapter.submitList(movies)
                Log.e("MOVIES-LIST-ACTIVITY", "Las de ROOM sin movies duplicadas por categorias son: :${movies.size}")
            }
        }
        movieViewModel.getAllMoviesRoom()
    }

    /**CARGA TODAS LAS CATEGORIAS DE MOVIES DESDE LA API  Y LAS CONCATENA EN UNA SOLA LISTA */
    private fun loadMoviesApi(isLoad: (List<List<MovieModel>?>) -> Unit) {
        movieViewModel.loadAllMovies()

        val moviesLiveData = listOf(
            movieViewModel.popularMovie,
            movieViewModel.topRatedMovie,
            movieViewModel.nowPlayingMovie,
            movieViewModel.upcomingMovie
        )

        val moviesResults = mutableListOf<List<MovieModel>?>()
        moviesLiveData.forEach { liveData ->
            liveData.observe(this) { movies ->
                moviesResults.add(movies)
                if (moviesResults.size == moviesLiveData.size) {
                    isLoad(moviesResults)
                    Log.e("MOVIES-LIST-ACTIVITY", "Las listas cargadas desde la API son: ${moviesResults.size}")

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


}
