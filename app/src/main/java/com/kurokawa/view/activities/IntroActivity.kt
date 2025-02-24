package com.kurokawa.view.activities

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.kurokawa.R
import com.kurokawa.databinding.ActivityIntroBinding
import com.kurokawa.utils.ViewPagerIntro

class IntroActivity : AppCompatActivity() {
    /** VARIABLES- BINDING - ADAPTER- VIEWMODEL-NAVCONTROLLER-------------------------------------*/
    private lateinit var _binding: ActivityIntroBinding
    private val binding: ActivityIntroBinding get() = _binding
    private lateinit var navController: NavController
    /**MAIN---------------------------------------------------------------------------------------*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()

    } private fun setupViewPager() {
        val viewPager = binding.viewPager
        val progressBar = binding.progressBar

        val adapter =ViewPagerIntro(this)
        viewPager.adapter = adapter

        // Actualizar la barra de progreso cuando el usuario desliza
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                val totalPages = adapter.itemCount
                val progress = ((position + positionOffset) / (totalPages - 1)) * 100
                progressBar.progress = progress.toInt()
            }
        })
    }
}