import androidx.room.Room
import com.kurokawa.data.remote.retrofit.RetrofitClient
import com.kurokawa.data.room.database.MyDataBase
import com.kurokawa.repository.LoginRepository
import com.kurokawa.repository.MovieDetailRepository
import com.kurokawa.repository.MovieListRepository
import com.kurokawa.repository.SignUpRepository
import com.kurokawa.viewModel.LoginViewModel
import com.kurokawa.viewModel.MovieDetailsViewModel
import com.kurokawa.viewModel.MovieListViewModel
import com.kurokawa.viewModel.SignUpViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

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

    // Inyectar DAOs
    single { get<MyDataBase>().movieDao() }
    single { get<MyDataBase>().userDao() }

    //  Inyectar Repositories
    single { MovieDetailRepository(get()) }
    single { MovieListRepository(get(), get()) }
    single { LoginRepository(get()) }
    single { SignUpRepository(get()) }

    //Inyectar ViewModels
    viewModel { MovieDetailsViewModel(get()) }
    viewModel { MovieListViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
}
