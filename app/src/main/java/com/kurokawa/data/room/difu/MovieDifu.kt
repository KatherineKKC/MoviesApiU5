package com.kurokawa.data.room.difu

import androidx.recyclerview.widget.DiffUtil
import com.kurokawa.data.room.entities.Movies

class MovieDifu(
    private val oldList: List<Movies>,
    private val newList: List<Movies>

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