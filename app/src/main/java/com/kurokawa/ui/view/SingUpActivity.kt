package com.kurokawa.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.kurokawa.application.MyApplication
import com.kurokawa.databinding.ActivitySingUpBinding
import com.kurokawa.data.room.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SingUpActivity : AppCompatActivity() {
    private lateinit var _binding : ActivitySingUpBinding
    private val binding: ActivitySingUpBinding get() = _binding
    private lateinit var applicacion : MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializamos la app
        applicacion = application as MyApplication


        binding.btnSing.setOnClickListener {
            var email = binding.etEmailSing.text.toString().trim()
            var password = binding.etPasswordSing.text.toString().trim()
            validateField(email,password)
        }

    }

    private fun validateField(email: String, password: String) {
        if (email.isEmpty() || email.isBlank() || password.isBlank() || password.isEmpty()){
            Snackbar.make(binding.root, "Los campos de texto deben estar llenos ", Snackbar.LENGTH_SHORT)
                .show()
        }else{
            validateUser(email, password)
        }
    }

    private fun validateUser(email: String, password: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            var user = applicacion .myDataBaseRoom.userDao().getUser(email,password)
            if (user != null) {
                    withContext(Dispatchers.Main){
                        Snackbar.make(binding.root, "El usuario ya existe", Snackbar.LENGTH_SHORT)
                            .show()
                    }

            } else {
                addUser(email,password)
            }
        }

    }

    private fun addUser(email: String, password: String){
        var user = User(0,email,password)
        lifecycleScope.launch(Dispatchers.IO){
            applicacion.myDataBaseRoom.userDao().addUser(user)
            withContext(Dispatchers.Main){
                Snackbar.make(binding.root, "El usuario se ha registrado con exito", Snackbar.LENGTH_SHORT)
                    .show()
                navigateToLogin()
            }
        }
    }

    private fun navigateToLogin() {
       val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}