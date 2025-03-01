import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kurokawa.data.remote.retrofit.RetrofitClient
import com.kurokawa.data.remote.service.MovieApiService
import com.kurokawa.data.room.database.MyDataBase
import com.kurokawa.databinding.FragmentNowPlayingMovieBinding
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
    single {
        Room.databaseBuilder(
            androidApplication(),
            MyDataBase::class.java,
            "MovieDatabase"
        ).fallbackToDestructiveMigration().build()
    }
    viewModelOf(::MovieListViewModel)

    //Api
    single { RetrofitClient.apiService }

    single<FirebaseAuth> { Firebase.auth }

    // Inyectar DAOs
    single { get<MyDataBase>().movieDao() }
    single { get<MyDataBase>().userDao() }

    //  Inyectar Repositories
    single { MovieDetailRepository(get()) }
    single { MovieListRepository(get(), get()) }
    single { LoginRepository(get(), get()) }
    single { SignUpRepository(get(), get())}

    //Inyectar ViewModels
    viewModel { MovieDetailsViewModel(get()) }
    viewModel { MovieListViewModel(get()) }
    viewModel { LoginViewModel(get())}
    viewModel { SignUpViewModel(get())}
}
