package com.kurokawa.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.kurokawa.databinding.ActivityLoginBinding
import com.kurokawa.application.MyApplication
import com.kurokawa.ui.viewModel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityLoginBinding
    private val binding: ActivityLoginBinding get() = _binding
    private lateinit var applicacion: MyApplication
    private val viewModel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applicacion = application as MyApplication
        binding.btnLogin.setOnClickListener{
            var email = binding.etEmail.text.toString().trim()
            var password = binding.etPassword.text.toString().trim()
                validateFields(email,password)
        }
        binding.btnSingup.setOnClickListener{
            navigateToSingUp()
        }
        observeLogin()
    }


    //Funcion para navegar a la vista SingUp Registro
    private fun navigateToSingUp() {
        val intent = Intent(this, SingUpActivity::class.java)
        startActivity(intent)
    }

    //Funcion para validar los campos de texto
    private fun validateFields( email: String, password:String){
        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModel.login(email, password) //LLama al metodo de viewmodel
        } else {
            Toast.makeText(this, "Ingrese email y contraseña", Toast.LENGTH_SHORT).show()
        }
    }



    private fun navigateToMovies() {
        val intent = Intent(this, MoviesListActivity::class.java)
        startActivity(intent)
    }

    private fun observeLogin(){
        viewModel.loginResult.observe(this, Observer { isLoggedIn ->
            if (isLoggedIn) {
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                navigateToMovies()
                finish()
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        })
    }



}