package com.kurokawa.repository

import com.kurokawa.data.network.RetrofitClient
import com.kurokawa.data.room.model.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository(private val apiKey:String) {

    fun getUpcomingMovies(onResult: (MovieResponse?) -> Unit) {
        RetrofitClient.instance.getUpcomingMovies(apiKey = apiKey)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    if (response.isSuccessful) {
                        onResult(response.body())
                    } else {
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    onResult(null)
                }
            })
    }

    fun getPopularMovies(onResult: (MovieResponse?) -> Unit) {
        RetrofitClient.instance.getPopularMovies(apiKey = apiKey)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    if (response.isSuccessful) {
                        onResult(response.body())
                    } else {
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    onResult(null)
                }
            })
    }

    fun getTopRatedMovies(onResult: (MovieResponse?) -> Unit) {
        RetrofitClient.instance.getTopRatedMovies(apiKey = apiKey)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    if (response.isSuccessful) {
                        onResult(response.body())
                    } else {
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    onResult(null)
                }
            })
    }

    fun getNowPlayingMovies(onResult: (MovieResponse?) -> Unit) {
        RetrofitClient.instance.getNowPlayingMovies(apiKey = apiKey)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    if (response.isSuccessful) {
                        onResult(response.body())
                    } else {
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    onResult(null)
                }
            })
    }
}
