package com.kurokawa.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurokawa.application.MyApplication
import com.kurokawa.data.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {
    /**INYECCION MANUAL DE REPOSITORIO*/
    private val repository: LoginRepository = LoginRepository(
        (MyApplication.instance).myDataBase.userDao()
    )

    /**VARIABLES DE OBSERVACION */
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult


    /**FUNCION PARA VERIFICAR EL USUARIO EN ROOM */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val isVerifier = repository.validateUser(email, password)
                _loginResult.postValue(isVerifier)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}

