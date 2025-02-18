package com.kurokawa.view.fragments.intro

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kurokawa.R
import com.kurokawa.view.activities.MoviesListActivity
import com.kurokawa.databinding.FragmentIntro3Binding

class Intro3Fragment : Fragment() {

    private lateinit var _binding : FragmentIntro3Binding
    private val binding: FragmentIntro3Binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIntro3Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setOnClickListener{
            navigateToMovieListActivity()
        }
    }

    private fun navigateToMovieListActivity(){
        val intent = Intent(requireContext(), MoviesListActivity::class.java)
        startActivity(intent)
    }


}