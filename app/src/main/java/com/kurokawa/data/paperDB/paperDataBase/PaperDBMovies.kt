package com.kurokawa.data.paperDB.paperDataBase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.kurokawa.data.paperDB.entities.MovieEntity
import io.paperdb.Paper
import java.io.ByteArrayOutputStream
import java.net.URL

class PaperDBMovies {
    /**OBSERVADORES-------------------------------------------------------------------------------*/
    private val _allMovies = MutableLiveData<List<MovieEntity>>(emptyList())
    val allMovies: LiveData<List<MovieEntity>> get() = _allMovies

    init {
        loadMovies()
    }


    /**FUNCIONES----------------------------------------------------------------------------------*/
    //GUARDAR LA PELISCULAS
    fun saveMovies(movieList: List<MovieEntity>) {
        // Obtener la lista almacenada o una lista vacía si no hay nada guardado
        val storedMovies =
            Paper.book().read("movies_list", emptyList<MovieEntity>())?.toMutableList()
        // Agregar nuevas películas sin duplicados
        movieList.forEach { newMovie ->
            val index = storedMovies?.indexOfFirst { it.idMovie == newMovie.idMovie }
            if (index == -1) {
                storedMovies.add(newMovie) // Agregar solo si no existe
            }
        }
        // Guardar la lista actualizada en PaperDB
        Paper.book().write("movies_list", storedMovies!!)
        _allMovies.postValue(storedMovies!!)
    }



    //CARGAR TODAS LAS PELICULAS DE PAPER DB
    fun loadMovies() {
        val movies = Paper.book().read("movies_list", emptyList<MovieEntity>())
        _allMovies.postValue(movies!!)
    }

   //OBTENER POR CATEGORIA DE PELICULA
    fun getMoviesByCategory(category: String): List<MovieEntity> {
        return _allMovies.value?.filter { it.category == category } ?: emptyList()
    }

  //OBTENER POR ID DE PELICULA
    fun getMovieById(idMovie: Long): MovieEntity? {
        return _allMovies.value?.find { it.idMovie == idMovie }
    }

  // OBTENER TODAS LA PELICULAS FAVORITAS
    fun getAllFavoriteMovies(): LiveData<List<MovieEntity>> {
        return _allMovies.map { movies -> movies.filter { it.isFavoriteMovie } }
    }

    /** 🔹 Actualizar el estado de favorito de una película */
    fun updateFavoriteStatus(idMovie: Long, isFavorite: Boolean) {
        val updatedMovies = _allMovies.value?.map { movie ->
            if (movie.idMovie == idMovie) movie.copy(isFavoriteMovie = isFavorite) else movie
        } ?: emptyList()

        Paper.book().write("movies_list", updatedMovies)
        _allMovies.postValue(updatedMovies)
    }

    /** 🔹 Guardar imagen como ByteArray */
    fun saveImageWithByteArray(url: String): ByteArray? {
        return try {
            val inputStream = URL(url).openStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /** 🔹 Recuperar imagen desde PaperDB */
    fun recuperarImagenDesdePaper(): Bitmap? {
        val imagenBytes: ByteArray? = Paper.book().read("imagen", null)
        return imagenBytes?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
    }
}
