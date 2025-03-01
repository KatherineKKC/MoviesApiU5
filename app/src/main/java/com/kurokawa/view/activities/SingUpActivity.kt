package com.kurokawa.view.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.kurokawa.databinding.ActivitySingUpBinding
import com.kurokawa.viewModel.SignUpViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SingUpActivity : AppCompatActivity() {

    /** VIEW BINDING Y VIEWMODEL ----------------------------------------------------------------*/
    private lateinit var binding: ActivitySingUpBinding
    private val viewModel: SignUpViewModel by viewModel()

    /** VARIABLES ------------------------------------------------------------------------------*/
    private var imageUri: Uri? = null // Guarda la imagen seleccionada
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
            binding.ivProfile.setImageURI(it) // Muestra la imagen en el ImageView
        }
    }

    /** MAIN ---------------------------------------------------------------------------------- */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        observeViewModel()
    }

    /** CONFIGURACIÓN DE UI ------------------------------------------------------------------ */
    private fun setupUI() {
        binding.btnSelectImage.setOnClickListener { pickImageLauncher.launch("image/*") }
        binding.btnSing.setOnClickListener { createUser() }
    }

    /** FUNCIÓN PARA REGISTRAR USUARIO ------------------------------------------------------ */
    private fun createUser() {
        if (!checkFields()) return

        val email = binding.etEmailSing.text.toString().trim()
        val password = binding.etPasswordSing2.text.toString().trim()
        val displayName = binding.etDisplayName.text.toString().trim()

        viewModel.registerUser(email, password, displayName, imageUri)
    }

    /** OBSERVAR EL ESTADO DEL REGISTRO ----------------------------------------------------- */
    private fun observeViewModel() {
        viewModel.signUpResult.observe(this, Observer { isSuccess ->
            if (isSuccess) {
                Snackbar.make(binding.root, "El usuario se ha registrado con éxito", Snackbar.LENGTH_SHORT).show()
                navigateToLogin()
            } else {
                Snackbar.make(binding.root, "Error al registrar usuario", Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    /** NAVEGAR A LOGIN -------------------------------------------------------------------- */
    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    /** VALIDAR CAMPOS DEL FORMULARIO ---------------------------------------------------- */
    private fun checkFields(): Boolean {
        var isValid = true
        val email = binding.etEmailSing.text.toString().trim().lowercase()
        val password = binding.etPasswordSing2.text.toString()


            if (email.isEmpty()) {
            binding.etEmailSing.error = "Ingrese un correo"
            isValid = false
        }
        if (password.length < 7 || !password.any { it.isLowerCase() } || !password.any { it.isUpperCase() }) {
            binding.etPasswordSing2.error = "Ingrese una contraseña con más de 6 caracteres, incluyendo mayúsculas y minúsculas"
            isValid = false
        }

        if (binding.etDisplayName.text.toString().trim().isEmpty()) {
            binding.etDisplayName.error = "Ingrese un nombre de usuario"
            isValid = false
        }

        return isValid
    }
}
