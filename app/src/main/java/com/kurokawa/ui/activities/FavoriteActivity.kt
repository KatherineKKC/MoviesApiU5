package com.kurokawa.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kurokawa.databinding.ActivityFavoriteBinding
import com.kurokawa.ui.favorite.FavoriteAdapter
import com.kurokawa.ui.viewModel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    //BINDING
    private lateinit var _binding: ActivityFavoriteBinding
    private val binding: ActivityFavoriteBinding get() = _binding

    //VIEMODEL
    private val viewModel: FavoriteViewModel by viewModels()

    //ADAPTER
    private val adapter = FavoriteAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding =ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpReciclerView()

        observeFavorites()
    }

    private fun setUpReciclerView() {
        binding.recyclerViewFavorites.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFavorites.adapter = adapter
    }

    private fun observeFavorites() {
        viewModel.favoriteMovies.observe(this){ favoriteList ->
            adapter.submitList(favoriteList)
        }
    }
}