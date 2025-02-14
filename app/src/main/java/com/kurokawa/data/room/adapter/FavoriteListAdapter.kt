package com.kurokawa.data.room.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kurokawa.data.room.difu.MovieDifu
import com.kurokawa.data.room.entities.MovieEntity
import com.kurokawa.databinding.ItemMoviesBinding

class FavoriteListAdapter(
    var listFavorites: MutableList<MovieEntity>,
    private val onFavorite: (MovieEntity) -> Unit) :RecyclerView.Adapter<FavoriteListHolder>(){
        
        
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteListHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteListHolder(binding)
    }

    override fun getItemCount(): Int =listFavorites.size

    override fun onBindViewHolder(holder: FavoriteListHolder, position: Int) {
        val movie = listFavorites[position]
        holder.bind(movie, onFavorite)
    }

    fun submitFavoriteList(newlistFavorites: List<MovieEntity>) {
        val diffResult = DiffUtil.calculateDiff(MovieDifu(listFavorites, newlistFavorites))
        listFavorites.clear()
        listFavorites.addAll(newlistFavorites)
        diffResult.dispatchUpdatesTo(this)
    }

}
