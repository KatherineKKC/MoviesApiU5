package com.kurokawa.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurokawa.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel (private val repository: LoginRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val isValid = repository.validateUser(email, password)
                _loginResult.postValue(isValid)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
