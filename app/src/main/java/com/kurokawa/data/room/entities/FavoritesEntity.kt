package com.kurokawa.data.room.entities

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorites",)
data class FavoritesEntity(
    val idMoviesFavorite: Long,
    val posterPath: String?,
    val title:String,
    val isFavorite: Boolean = true,

) : Parcelable
