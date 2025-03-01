package com.kurokawa.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results") val results:List<MovieModel>,
    @SerializedName("page") val page:Int,
    @SerializedName("total:pages") val totalPages:Int
)
