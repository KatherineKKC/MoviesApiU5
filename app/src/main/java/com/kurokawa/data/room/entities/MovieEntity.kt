package com.kurokawa.data.room.entities
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "movies",
    primaryKeys =["idMovie", "category"])
data class MovieEntity(
    val idMovie: Long,
    val posterPath: String?,
    val title: String,
    val originalTitle: String?,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double,
    var isFavoriteMovie: Boolean = false,
    var category: String
) : Parcelable
