package com.kurokawa.data.room.entities
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "movies")
data class Movies(
    @PrimaryKey
    @SerializedName("id")               val idMovie: Int,

    @SerializedName("poster_path")      val posterPath: String?,   // Mapeado desde "poster_path"
    @SerializedName("title")            val title: String,
    @SerializedName("original_title")   val originalTitle: String?, // Puede ser nulo si falta
    @SerializedName("overview")         val overview: String,
    @SerializedName("release_date")     val releaseDate: String,
    @SerializedName("vote_average")     val voteAverage: Double,    // Mejor en Double para decimales

    var isFavoriteMovie: Boolean = false,  // Valor por defecto porque no viene de la API
    var category: String = ""              // También controlado internamente
)
