package com.kurokawa.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.kurokawa.application.MyApplication
import com.kurokawa.data.room.entities.UserEntity
import com.kurokawa.databinding.ActivitySingUpBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SingUpActivity : AppCompatActivity() {
    /**VARIABLES----------------------------------------------------------------------------------*/
    private lateinit var _binding: ActivitySingUpBinding
    private val binding: ActivitySingUpBinding get() = _binding
    private lateinit var applicacion: MyApplication


    /**MAIN---------------------------------------------------------------------------------------*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**INICIALIZACION DE APPLICATION*/
        applicacion = application as MyApplication

        /**BOTON VERIFICA QUE LOS CAMPOS ESTEN LLENOS*/
        binding.btnSing.setOnClickListener {
            var email = binding.etEmailSing.text.toString().trim()
            var password = binding.etPasswordSing.text.toString().trim()
            validateField(email, password)
        }

    }


    /**FUNCIONES----------------------------------------------------------------------------------*/
    //Valida los campos
    private fun validateField(email: String, password: String) {
        if (email.isEmpty() || email.isBlank() || password.isBlank() || password.isEmpty()) {
            Snackbar.make(
                binding.root,
                "Los campos de texto deben estar llenos ",
                Snackbar.LENGTH_SHORT
            )
                .show()
        } else {
            validateUser(email, password)
        }
    }

    //Verifica si el usuario ya existia para no realizar doble registro
    private fun validateUser(email: String, password: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            var user = applicacion.myDataBase.userDao().getUser(email, password)
            if (user != null) {
                withContext(Dispatchers.Main) {
                    Snackbar.make(binding.root, "El usuario ya existe", Snackbar.LENGTH_SHORT)
                        .show()
                }
            } else {
                addUser(email, password)
            }
        }
    }

    //Añade el usuario a room
    private fun addUser(email: String, password: String) {
        var user = UserEntity(0, email, password)
        lifecycleScope.launch(Dispatchers.IO) {
            applicacion.myDataBase.userDao().addUser(user)
            withContext(Dispatchers.Main) {
                Snackbar.make(
                    binding.root,
                    "El usuario se ha registrado con exito",
                    Snackbar.LENGTH_SHORT
                )
                    .show()
                navigateToLogin()
            }
        }
    }

    //Regresa al login despues de ser registrado para proceder con el inicio de sesión
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}