package com.kurokawa.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.kurokawa.application.MyApplication
import com.kurokawa.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(): ViewModel() {
    private lateinit var applicacion : MyApplication

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult : LiveData<Boolean> get() = _loginResult

    private lateinit var  repository: LoginRepository

    fun login(email: String,password :String){
        viewModelScope.launch {
            val isVerifier = repository.validateUser(email,password)
            _loginResult.postValue(isVerifier)
        }
    }


}

