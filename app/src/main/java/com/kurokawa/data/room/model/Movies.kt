package com.kurokawa.data.room.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movies(

    @PrimaryKey  var idMovie :Int,
    val posterPath: String,
    val title:String,
    val originalTitle:String,
    val overview:String,
    val releaseDate:String,
    val voteAverage:Int,
    var isFavoriteMovie: Boolean = false
)
