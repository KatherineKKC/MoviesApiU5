package com.kurokawa.model
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieModel(
    @SerializedName("id")
    val id:Long,

    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview:String,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

): Parcelable
