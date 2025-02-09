package com.kurokawa.ui.movieList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kurokawa.R
import com.kurokawa.databinding.MoviesItemsBinding
import com.kurokawa.data.room.difu.MovieDifu
import com.kurokawa.data.room.model.Movies

class MoviesAdapter(var listMovies: MutableList<Movies>,
    private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<MoviesAdapter.MoviesHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val inflater =LayoutInflater.from(parent.context)
        val binding = MoviesItemsBinding.inflate(inflater, parent, false)
        return MoviesHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {

        val movie = listMovies[position]
        holder.binding.tvTitleMovie.text = movie.title

        //Imagen
        Glide.with(holder.itemView.context)
            .load(movie.posterPath)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .into(holder.binding.imgMovie)
        //Accion al hacer click
        holder.binding.root.setOnClickListener{
            onItemClick(movie.title)
        }
    }

    override fun getItemCount(): Int {
        return listMovies.size
    }

    fun submitList(newListMovies: List<Movies>){
        val difCalback = MovieDifu(listMovies, newListMovies)
        val difResult = DiffUtil.calculateDiff(difCalback)
        listMovies.clear()
        listMovies.addAll(newListMovies)
        difResult.dispatchUpdatesTo(this)
    }
    //_________________________________________HOLDER____________________________________________//
    inner class MoviesHolder(val binding: MoviesItemsBinding): RecyclerView.ViewHolder(binding.root){}
}