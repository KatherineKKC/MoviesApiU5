package com.kurokawa.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurokawa.repository.SignUpRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class SignUpViewModel(private val repository: SignUpRepository) : ViewModel() {

    private val _signUpResult = MutableLiveData<Boolean>()
    val signUpResult: LiveData<Boolean> get() = _signUpResult

    fun registerUser(email: String, password: String, displayName: String, imageUri: Uri?) {
        viewModelScope.launch {
            val isSuccess = repository.registerUser(email, password, displayName, imageUri) // 🔹 Convierte URI a String
            viewModelScope.launch(Dispatchers.Main) {
                _signUpResult.postValue(isSuccess)
            }
        }
    }
}
