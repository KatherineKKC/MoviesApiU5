package com.kurokawa.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kurokawa.view.fragments.intro.Intro1Fragment
import com.kurokawa.view.fragments.intro.Intro2Fragment
import com.kurokawa.view.fragments.intro.Intro3Fragment

class ViewPagerIntro(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3 // Número de Fragments

    //ORGANIZA LA NAVEGACION DE LOS FRAGMENTOS
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Intro1Fragment()
            1 -> Intro2Fragment()
            2 -> Intro3Fragment()
            else -> throw IllegalStateException("Posición desconocida: $position")
        }
    }
}
