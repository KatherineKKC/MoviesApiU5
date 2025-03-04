package com.kurokawa.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.kurokawa.databinding.ActivityLoginBinding
import com.kurokawa.viewModel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    /**VARIABLES DECLARADAS-----------------------------------------------------------------------*/
    private lateinit var _binding: ActivityLoginBinding
    private val binding: ActivityLoginBinding get() = _binding
    private val viewModel: LoginViewModel by viewModel()


    /**MAIN---------------------------------------------------------------------------------------*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Boton para entrar en la app
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            validateFields(email, password)
        }

        //Navega a la activity de registro
        binding.btnSingup.setOnClickListener {
            navigateToSingUp()
        }

        //Funciones llamadas
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


    //Validar el usuario y navegar a la vista principal
    private fun observeLogin() {
        viewModel.loginResult.observe(this, Observer { isLoggedIn ->
            if (isLoggedIn) {
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                navigateToIntro()
                finish()
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navigateToIntro() {
        val intent = Intent(this, IntroActivity::class.java)
        startActivity(intent)
    }


}