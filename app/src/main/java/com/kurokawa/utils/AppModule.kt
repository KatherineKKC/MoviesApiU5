import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.kurokawa.data.remote.retrofit.RetrofitClient
import com.kurokawa.data.dataStore.store.MovieDataStore
import com.kurokawa.data.dataStore.store.UserDataStore
import com.kurokawa.data.remote.service.MovieApiService
import com.kurokawa.repository.MovieListRepository
import com.kurokawa.repository.LoginRepository
import com.kurokawa.repository.MovieDetailRepository
import com.kurokawa.viewModel.LoginViewModel
import com.kurokawa.viewModel.MovieListViewModel
import com.kurokawa.repository.SignUpRepository
import com.kurokawa.utils.Constants
import com.kurokawa.viewModel.MovieDetailsViewModel
import com.kurokawa.viewModel.SignUpViewModel

val appModule = module {
    // Define la API Key como una dependencia
    single { Constants.API_KEY }

    single<MovieApiService> { RetrofitClient.apiService }
    // Inyectar DataStore
    single { MovieDataStore(androidApplication()) }
    single { UserDataStore(androidApplication()) }

    // Inyectar Repositories
    single { MovieDetailRepository(get()) }
    single { MovieListRepository(get(), get(), get()) } // Pasa la API Key como tercer parámetro
    single { LoginRepository(get()) }
    single { SignUpRepository(get()) }

    // Inyectar ViewModels
    viewModel { MovieDetailsViewModel(get()) }
    viewModel { MovieListViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
}