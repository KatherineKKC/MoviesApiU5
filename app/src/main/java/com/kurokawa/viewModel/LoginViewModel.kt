package com.kurokawa.viewModel

import android.text.BoringLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurokawa.application.MyApplication
import com.kurokawa.data.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(): ViewModel() {
    private val repository: LoginRepository = LoginRepository(
        (MyApplication.instance).myDataBaseRoom.userDao()
    )

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult : LiveData<Boolean> get() = _loginResult

    fun login(email: String,password :String){

        viewModelScope.launch {
            try {
                val isVerifier = repository.validateUser(email, password)
                _loginResult.postValue(isVerifier)

            }catch (e: Exception){
                e.printStackTrace()
            }


        }
    }


}

