package com.kurokawa.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurokawa.repository.SignUpRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: SignUpRepository) : ViewModel() {
    /**VARIABLES DECLARADAS-----------------------------------------------------------------------*/
    private val _signUpResult = MutableLiveData<Boolean>()
    val signUpResult: LiveData<Boolean> get() = _signUpResult


    /**FUNCIONES----------------------------------------------------------------------------------*/
    fun registerUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isRegistered = repository.registerUser(email,password)
            if (isRegistered) {
                _signUpResult.postValue(false) // El usuario ya existe
            } else {
                repository.registerUser(email, password)
                _signUpResult.postValue(true) // Registro exitoso
            }
        }
    }
}
