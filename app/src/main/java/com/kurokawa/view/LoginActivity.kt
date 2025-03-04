package com.kurokawa.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.kurokawa.application.MyApplication
import com.kurokawa.databinding.ActivityLoginBinding
import com.kurokawa.viewModel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    /**VARIABLES----------------------------------------------------------------------------------*/
    private lateinit var _binding: ActivityLoginBinding
    private val binding: ActivityLoginBinding get() = _binding
    private lateinit var applicacion: MyApplication
    private val viewModel: LoginViewModel by viewModels()


    /**MAIN---------------------------------------------------------------------------------------*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**INICIALIZACION DE APPLICATION*/
        applicacion = application as MyApplication

        /**BOTON VERIFICA QUE LOS CAMPOS ESTEN LLENOS*/
        binding.btnLogin.setOnClickListener {
            var email = binding.etEmail.text.toString().trim()
            var password = binding.etPassword.text.toString().trim()
            validateFields(email, password)
        }

        /**BOTON PARA REGISTRARSE Y NAVEGAR A LA VISTA DE REGISTRO*/
        binding.btnSingup.setOnClickListener {
            navigateToSingUp()
        }

        /**METODO PARA OBSERVAR EL LOGIN Y DAR ACCESO */
        observeLogin()
    }


    /**FUNCIONES----------------------------------------------------------------------------------*/
    //Funcion para navegar a la vista SingUp Registro
    private fun navigateToSingUp() {
        val intent = Intent(this, SingUpActivity::class.java)
        startActivity(intent)
    }

    //Funcion para validar los campos de texto
    private fun validateFields(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModel.login(email, password) //LLama al metodo de viewmodel
        } else {
            Toast.makeText(this, "Ingrese email y contraseña", Toast.LENGTH_SHORT).show()
        }
    }

    //Funcion para navegar a la vista principal despues de la verificacion
    private fun navigateToMovies() {
        val intent = Intent(this, MoviesListActivity::class.java)
        startActivity(intent)
    }

    //Observar si la verificacion de usuario ha sido correcta
    private fun observeLogin() {
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