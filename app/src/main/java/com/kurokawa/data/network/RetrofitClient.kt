package com.kurokawa.data.network

import android.provider.SyncStateContract
import com.kurokawa.utils.Constans
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val APY_KEY = Constans.API_KEY

    // Interceptor para agregar la API Key automáticamente
    private val apiKeyInterceptor = Interceptor { chain ->
        val originalRequest: Request = chain.request()
        val newUrl = originalRequest.url().newBuilder()
            .addQueryParameter("api_key", APY_KEY)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    // Cliente HTTP que usa el interceptor
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .build()

    // Retrofit instance
    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Agregamos el cliente con el interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}
