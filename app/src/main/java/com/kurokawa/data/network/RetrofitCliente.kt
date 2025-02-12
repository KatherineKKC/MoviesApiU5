package com.kurokawa.data.network

import com.kurokawa.utils.Constans.API_KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val AUTH_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwNWE2ZTIxMmMzN2ExYTQ2NzVmMDgxMDk5MDFhZGVmYyIsIm5iZiI6MTczODE3NTg4NC4yMDk5OTk4LCJzdWIiOiI2NzlhNzU4Yzg3ZTA4MDRlYzNiZDY3YWEiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.jlLAeDvM19xIZ37RGjPSiMlROJBRfGlXgCWpN_pApMI" // Reemplaza con tu token real
    private val apiKeyInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY)  // Añade automáticamente la API Key
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val authInterceptor = Interceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", AUTH_TOKEN)
            .build()
        chain.proceed(newRequest)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    val apiService: MovieApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiService::class.java)
    }
}
