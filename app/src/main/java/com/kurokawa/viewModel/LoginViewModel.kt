package com.kurokawa.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.kurokawa.data.room.entities.UserEntity
import com.kurokawa.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    //Variables de liveData
    private val _userRoom = MutableLiveData<FirebaseUser?>()
    var userRoom : MutableLiveData<FirebaseUser?> = _userRoom
    private val _isAnonymous = MutableLiveData<Boolean>()
    var isAnonymous :LiveData<Boolean> = _isAnonymous

    //Autentificamos el usuario con firebase
    fun isUserLogged(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO){
            val user = repository.sigInFirebaseEmailAndPassword(email,password)
            if (user != null){
                withContext(Dispatchers.Main){
                    _userRoom.value = user
                }
            }
        }
    }


    // Cierra la sesión
    fun signOut() {
        repository.signOut()
    }

    fun sigInAnonymous() {
        viewModelScope.launch(Dispatchers.IO){
             val anonymous = repository.signInLikeAnonymous()
            if (anonymous != null)
                 withContext(Dispatchers.Main){
                     _isAnonymous.value = anonymous
                 }
        }
    }
}