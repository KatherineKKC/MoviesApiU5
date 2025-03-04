package com.kurokawa.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.kurokawa.R
import com.kurokawa.databinding.FragmentProfileBinding
import com.kurokawa.view.fragments.intro.Intro3Fragment
import com.kurokawa.viewModel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ProfileFragment : Fragment() {
    /**VARIABLES----------------------------------------------------------------------------------*/
    private lateinit var _binding: FragmentProfileBinding
    private val binding: FragmentProfileBinding get() = _binding
    private val viewModel: LoginViewModel by sharedViewModel()

    /**VISTA--------------------------------------------------------------------------------------*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    /**MAIN---------------------------------------------------------------------------------------*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserProfile()
        setupDeleteButton()
        Log.e("PROFILE-FRAGMENT", "Se está viendo en profile")
    }


    /**FUNCIONES----------------------------------------------------------------------------------*/
    private fun loadUserProfile() {
        viewModel.getUser()
        viewModel.userRoom.observe(viewLifecycleOwner) { userProfile ->
            Log.e("PROFILE-FRAGMENT", "Se ha recibido del ViewModel: ${userProfile}")
            if (userProfile != null) {
                binding.tvUserName.text = userProfile.displayName ?: "Sin nombre"
                binding.tvMail2.text = userProfile.email ?: "Sin email"
                binding.tvIdUser.text = userProfile.idUser.toString()

                Glide.with(requireContext())
                    .load(userProfile.imagePath ?: "")
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(binding.ivProfile)

                Log.e(
                    "PROFILE-FRAGMENT",
                    "Actualizando UI con el usuario: ${userProfile.idFirebaseUser}"
                )
            }
        }
    }

    private fun setupDeleteButton() {
        // Asumiendo que en tu layout tienes un botón con id btnDeleteUser
        binding.btnDeleteAccount.setOnClickListener {
            viewModel.userRoom.value?.let { user ->
                viewModel.deleteUser(user.idFirebaseUser)
                Toast.makeText(requireContext(), "Usuario eliminado", Toast.LENGTH_SHORT).show()
                navigateToIntro()
            } ?: run {
                Toast.makeText(requireContext(), "No hay usuario para eliminar", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun navigateToIntro() {
        val introFragment = Intro3Fragment()
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.profileFragment,
                introFragment
            ) // Asegúrate de que el ID corresponde al contenedor de fragmentos
            .addToBackStack(null) // Permite regresar al fragmento anterior si se presiona "Atrás"
            .commit()
    }

}