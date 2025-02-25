import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import androidx.room.Room
import com.kurokawa.data.remote.retrofit.RetrofitClient
import com.kurokawa.data.sharedPreferences.storage.SharedPreferencesStorageMovies
import com.kurokawa.data.sharedPreferences.storage.SharedPreferencesStorageUser
import com.kurokawa.repository.MovieListRepository
import com.kurokawa.repository.LoginRepository
import com.kurokawa.repository.MovieDetailRepository
import com.kurokawa.viewModel.LoginViewModel
import com.kurokawa.viewModel.MovieListViewModel
import com.kurokawa.repository.SignUpRepository
import com.kurokawa.viewModel.MovieDetailsViewModel
import com.kurokawa.viewModel.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf

val appModule = module {

    viewModelOf(::MovieListViewModel)

    //Api
    single { RetrofitClient.apiService }

    // Inyectar SharedPreferences Storage
    single { SharedPreferencesStorageMovies(androidApplication()) }
    single { SharedPreferencesStorageUser(androidApplication()) }

    //  Inyectar Repositories
    single { MovieDetailRepository(get()) }
    single { MovieListRepository(get(), get()) }
    single { LoginRepository(get()) }
    single { SignUpRepository(get())}

    //Inyectar ViewModels
    viewModel { MovieDetailsViewModel(get()) }
    viewModel { MovieListViewModel(get()) }
    viewModel { LoginViewModel(get())}
    viewModel { SignUpViewModel(get())}
}
