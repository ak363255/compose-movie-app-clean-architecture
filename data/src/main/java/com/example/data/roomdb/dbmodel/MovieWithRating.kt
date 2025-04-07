package com.example.data.roomdb.dbmodel

import androidx.room.Embedded
import androidx.room.Relation
import com.example.domain.models.MovieDetail

data class MovieWithRating(
    @Embedded
    val movieEntitty: MovieEntity,
    @Relation(
        parentColumn = "imdbID",
        entityColumn = "movieId",
    )
    val ratings : List<RatingEntity>
)

fun MovieWithRating.toMovieDetail():MovieDetail{
    val rating = this.ratings.map { it.toMovieRaingData() }
    return  this.movieEntitty.toMovieDetail(rating)
}


