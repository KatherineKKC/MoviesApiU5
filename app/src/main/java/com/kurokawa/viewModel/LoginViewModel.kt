package com.kurokawa.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurokawa.data.room.entities.UserEntity
import com.kurokawa.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    /**OBSERVADORES-------------------------------------------------------------------------------*/
    //OBSERVADOR DE USUARIO EN ROOM
    private val _userRoom = MutableLiveData<UserEntity?>()
    var userRoom: LiveData<UserEntity?> = _userRoom

    //OBSERVADOR DE LOGIN ANONIMO DESDE FIREBAS
    private val _signInAnonymous = MutableLiveData<Boolean>()
    var signInAnonymous: LiveData<Boolean> = _signInAnonymous



    /**FUNCIONES----------------------------------------------------------------------------------*/
    //OBTENER USUARI0 POR ID RETORNA LA ENTIDAD
    fun getUser() {
        viewModelScope.launch {
            val user = repository.getUserById()
            withContext(Dispatchers.Main) {
                _userRoom.value = user
            }
        }
    }


    //AUTENTICA EL INCIO DE SESION EN FIREBASE
    fun isUserLogged(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userEntity = repository.sigInFirebaseEmailAndPassword(email, password)
            withContext(Dispatchers.Main) {
                _userRoom.value = userEntity
                Log.e("LOGIN-VIEW-MODEL", "El usuario recibido del repositorio $userEntity")
            }
        }
    }

    // CIERRA LA SESION(PROFILE)
    fun signOut() {
        repository.signOut()
    }


    //INGRESA COMO ANONIMO POR FIREBASE
    fun sigInAnonymous() {
        viewModelScope.launch(Dispatchers.IO) {
            val idAnonymous = repository.signInLikeAnonymous()
            if (!idAnonymous.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    _signInAnonymous.value = true
                }
            }
        }
    }

    //ELIMINA EL USUARIO DE FIREBASE Y ROOM
    fun deleteUser(userFirebaseId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUserFromRoom(userFirebaseId)
            withContext(Dispatchers.Main) {
                // Se actualiza el LiveData para reflejar que ya no hay usuario
                _userRoom.value = null
            }
        }
    }

}