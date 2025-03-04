package com.kurokawa.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.kurokawa.databinding.ActivitySingUpBinding
import com.kurokawa.viewModel.SignUpViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SingUpActivity : AppCompatActivity() {
    /**VARIABLES DECLARADAS-----------------------------------------------------------------------*/
    private lateinit var _binding: ActivitySingUpBinding
    private val binding: ActivitySingUpBinding get() = _binding
    private val signUpViewModel: SignUpViewModel by viewModel()

    /**MAIN---------------------------------------------------------------------------------------*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Boton para registrar un usuario
        binding.btnSing.setOnClickListener {
            val email = binding.etEmailSing.text.toString().trim()
            val password = binding.etPasswordSing.text.toString().trim()
            validateField(email, password)
        }

        //Funcion llamada
        observeSignUpResult()
    }

    /**FUNCIONES----------------------------------------------------------------------------------*/
    //Verifica que los campos no esten vacios y registra el usuario
    private fun validateField(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Snackbar.make(
                binding.root,
                "Los campos de texto deben estar llenos",
                Snackbar.LENGTH_SHORT
            ).show()
        } else {
            signUpViewModel.registerUser(email, password)
        }
    }

    //Verifica si el usuario ya existia o navega de nuevo a login para que inicie sesion
    private fun observeSignUpResult() {
        signUpViewModel.signUpResult.observe(this, Observer { isSuccess ->
            if (isSuccess) {
                Snackbar.make(
                    binding.root,
                    "El usuario se ha registrado con éxito",
                    Snackbar.LENGTH_SHORT
                ).show()
                navigateToLogin()
            } else {
                Snackbar.make(binding.root, "El usuario ya existe", Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    //Navega a la vista de Login
    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
