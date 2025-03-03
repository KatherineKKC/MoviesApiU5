package com.kurokawa.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kurokawa.viewModel.LoginViewModel
import com.kurokawa.R
import com.kurokawa.databinding.ActivityLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var  _binding : ActivityLoginBinding
    private val binding: ActivityLoginBinding get() = _binding

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isLoggedUser()
        navigateToSignUp()
        signInLikeAnonimous()
    }

    //FUNCIONES
    private fun signInLikeAnonimous(){
        binding.btnAnonymous.setOnClickListener {
            viewModel.sigInAnonymous()
            viewModel.signInAnonymous.observe(this){signInAnonymous ->
                if (signInAnonymous){
                    navigateToMovieList()
                }else{
                    Toast.makeText(this, "Error al ingresar sin registro", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isLoggedUser(){
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim().lowercase()
            val password = binding.etPassword.text.toString().trim()

           if (checkField()){
               viewModel.isUserLogged(email,password)
               viewModel.userRoom.observe(this){ isLogged->
                   if (isLogged != null){
                       Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                       clearBox()
                       navigateToMovieList()
                   }else{
                       Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show()

                   }
               }
           }else{
               Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()

           }
        }
    }

    private fun clearBox(){
        binding.etEmail.setText("")
        binding.etPassword.setText("")
    }


    private fun navigateToMovieList() {
        val intent = Intent(this, MoviesListActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToSignUp() {
        binding.btnSingup.setOnClickListener {
            val intent = Intent(this, SingUpActivity::class.java)
            startActivity(intent)
        }
    }


    private fun checkField(): Boolean {
        val email = binding.etEmail.text.toString().trim().lowercase()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty()) {
            binding.etEmail.error = "Ingrese un correo"
            return false
        }
        if (password.isEmpty()) {
            binding.etPassword.error = "Ingrese una contraseña"
            return false
        }
        return true
    }

}