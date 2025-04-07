package com.example.data.roomdb.dbmodel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.models.MovieDetail
import com.example.domain.models.MovieRatingData

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val imdbID: String,
    val title: String,
    val year: String,
    val rated: String,
    val released: String,
    val runtime: String,
    val genre: String,
    val director: String,
    val writer: String,
    val actors: String,
    val plot: String,
    val language: String,
    val country: String,
    val awards: String,
    val poster: String,
    val metascore: String,
    val imdbRating: String,
    val imdbVotes: String,
    val type: String,
    val dvd: String,
    val boxOffice: String,
    val production: String,
    val website: String,
    val response: String
)


fun MovieEntity.toMovieDetail(ratings: List<MovieRatingData>): MovieDetail {
    return MovieDetail(
        title = title,
        year = year,
        rated = rated,
        released = released,
        runtime = runtime,
        genre = genre,
        director = director,
        writer = writer,
        actors = actors,
        plot = plot,
        language = language,
        country = country,
        awards = awards,
        poster = poster,
        ratings = ArrayList(ratings),
        metascore = metascore,
        imdbRating = imdbRating,
        imdbVotes = imdbVotes,
        imdbID = imdbID,
        type = type,
        dvd = dvd,
        boxOffice = boxOffice,
        production = production,
        website = website,
        response = response
    )
}

fun MovieDetail.toMovieEntity(): MovieEntity {
    return MovieEntity(
        imdbID = imdbID,
        title = title,
        year = year,
        rated = rated,
        released = released,
        runtime = runtime,
        genre = genre,
        director = director,
        writer = writer,
        actors = actors,
        plot = plot,
        language = language,
        country = country,
        awards = awards,
        poster = poster,
        metascore = metascore,
        imdbRating = imdbRating,
        imdbVotes = imdbVotes,
        type = type,
        dvd = dvd,
        boxOffice = boxOffice,
        production = production,
        website = website,
        response = response
    )
}