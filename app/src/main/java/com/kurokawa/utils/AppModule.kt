package com.kurokawa.utils

import com.kurokawa.data.paperDB.paperDataBase.PaperDBMovies
import com.kurokawa.data.paperDB.paperDataBase.PaperDBUser
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.kurokawa.data.remote.retrofit.RetrofitClient
import com.kurokawa.repository.*
import com.kurokawa.viewModel.*
import org.koin.androidx.viewmodel.dsl.viewModelOf

object AppModule {
    val module = module {

        viewModelOf(::MovieListViewModel)

        // API Service
        single { RetrofitClient.apiService }

        // Inyectar PaperDB
        single { PaperDBMovies() }
        single { PaperDBUser() }

        // Inyectar Repositories
        single { MovieDetailRepository(get()) }
        single { MovieListRepository(get(), get()) }
        single { LoginRepository(get()) }
        single { SignUpRepository(get()) }

        // Inyectar ViewModels
        viewModel { MovieDetailsViewModel(get()) }
        viewModel { MovieListViewModel(get()) }
        viewModel { LoginViewModel(get()) }
        viewModel { SignUpViewModel(get()) }
    }
}
