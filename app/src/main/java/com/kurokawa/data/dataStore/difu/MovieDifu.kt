package com.kurokawa.data.dataStore.difu

import androidx.recyclerview.widget.DiffUtil
import com.kurokawa.data.dataStore.entities.MovieEntity

class MovieDifu(
    private val oldList: MutableList<MovieEntity>,
    private val newList: List<MovieEntity>

) :DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].idMovie == newList[newItemPosition].idMovie
    }


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}