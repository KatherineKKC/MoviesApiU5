package com.kurokawa.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.kurokawa.ui.movieList.MoviesListActivity
import com.kurokawa.ui.sing.SingUpActivity
import com.kurokawa.databinding.ActivityLoginBinding
import com.kurokawa.application.MyApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityLoginBinding
    private val binding: ActivityLoginBinding get() = _binding
    private lateinit var applicacion: MyApplication

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
    }

    //Funcion para navegar a la vista SingUp Registro
    private fun navigateToSingUp() {
        val intent = Intent(this, SingUpActivity::class.java)
        startActivity(intent)
    }

    //Funcion para validar los campos de texto
    private fun validateFields( email: String, password:String) {
        if (email.isEmpty() || email.isBlank() || password.isBlank() || password.isEmpty()){
            Snackbar.make(binding.root, "Los campos de texto deben estar llenos ", Snackbar.LENGTH_SHORT)
                .show()
        }else{
            validateUser(email, password)
        }
    }



    private fun validateUser(email:String, password:String) {

            lifecycleScope.launch(Dispatchers.IO) {
                var user = applicacion .room.userDao().getUser(email,password)
                if (user != null) {
                        withContext(Dispatchers.Main){
                            navigateToMovies()
                            Snackbar.make(binding.root, "Bienvenido", Snackbar.LENGTH_SHORT)
                                .show()
                        }

                } else {
                    withContext(Dispatchers.Main){
                        Snackbar.make(binding.root, "El usuario no existe, por favor registrate", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            }

    }

    private fun navigateToMovies() {
        val intent = Intent(this, MoviesListActivity::class.java)
        startActivity(intent)
    }



}