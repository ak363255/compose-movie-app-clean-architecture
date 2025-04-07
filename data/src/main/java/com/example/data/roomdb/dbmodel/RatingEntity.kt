package com.example.data.roomdb.dbmodel

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.domain.models.MovieRatingData

@Entity(tableName = "movie_rating", foreignKeys = [
    ForeignKey(
        entity = MovieEntity::class,
        parentColumns = ["imdbID"],
        childColumns = ["movieId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class RatingEntity(
    @PrimaryKey(autoGenerate = true)val id:Int = 0,
    val movieId : String,
    val source: String,
    val value: String
)

fun RatingEntity.toMovieRaingData(): MovieRatingData {
    return MovieRatingData(
        source = this.source,
        value  = this.value
    )
}

fun MovieRatingData.toRatingEntity(id:String):RatingEntity{
    return RatingEntity(
        movieId = id,
        source = this.source,
        value = this.value
    )

}