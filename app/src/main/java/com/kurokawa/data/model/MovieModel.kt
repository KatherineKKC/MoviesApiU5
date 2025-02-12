package com.kurokawa.data.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class MovieModel(

    @PrimaryKey
    @SerializedName("id")               val idMovie: Int,

    @SerializedName("poster_path")      val posterPath: String?,
    @SerializedName("title")            val title: String,
    @SerializedName("original_title")   val originalTitle: String?,
    @SerializedName("overview")         val overview: String,
    @SerializedName("release_date")     val releaseDate: String,
    @SerializedName("vote_average")     val voteAverage: Double,
    val category: String? = null

)


