package com.example.data.datamodels

import com.example.domain.models.MovieDetail
import com.example.domain.models.MovieRatingData
import com.google.gson.annotations.SerializedName

data class MovieDetailData(
    @SerializedName("Title") var Title: String? = null,
    @SerializedName("Year") var Year: String? = null,
    @SerializedName("Rated") var Rated: String? = null,
    @SerializedName("Released") var Released: String? = null,
    @SerializedName("Runtime") var Runtime: String? = null,
    @SerializedName("Genre") var Genre: String? = null,
    @SerializedName("Director") var Director: String? = null,
    @SerializedName("Writer") var Writer: String? = null,
    @SerializedName("Actors") var Actors: String? = null,
    @SerializedName("Plot") var Plot: String? = null,
    @SerializedName("Language") var Language: String? = null,
    @SerializedName("Country") var Country: String? = null,
    @SerializedName("Awards") var Awards: String? = null,
    @SerializedName("Poster") var Poster: String? = null,
    @SerializedName("Ratings") var Ratings: ArrayList<Ratings>? = null,
    @SerializedName("Metascore") var Metascore: String? = null,
    @SerializedName("imdbRating") var imdbRating: String? = null,
    @SerializedName("imdbVotes") var imdbVotes: String? = null,
    @SerializedName("imdbID") var imdbID: String? = null,
    @SerializedName("Type") var Type: String? = null,
    @SerializedName("DVD") var DVD: String? = null,
    @SerializedName("BoxOffice") var BoxOffice: String? = null,
    @SerializedName("Production") var Production: String? = null,
    @SerializedName("Website") var Website: String? = null,
    @SerializedName("Response") var Response: String? = null
)
data class Ratings(
    @SerializedName("Source") var Source: String? = null,
    @SerializedName("Value") var Value: String? = null
)
fun Ratings.toDomain(): MovieRatingData {
    return MovieRatingData(
        source = Source.orEmpty(),
        value = Value.orEmpty()
    )
}
fun MovieDetailData.toDomain(): MovieDetail {
    return MovieDetail(
        title = Title.orEmpty(),
        year = Year.orEmpty(),
        rated = Rated.orEmpty(),
        released = Released.orEmpty(),
        runtime = Runtime.orEmpty(),
        genre = Genre.orEmpty(),
        director = Director.orEmpty(),
        writer = Writer.orEmpty(),
        actors = Actors.orEmpty(),
        plot = Plot.orEmpty(),
        language = Language.orEmpty(),
        country = Country.orEmpty(),
        awards = Awards.orEmpty(),
        poster = Poster.orEmpty(),
        ratings = Ratings?.map { it.toDomain() }?.let { ArrayList(it) } ?: arrayListOf(),
        metascore = Metascore.orEmpty(),
        imdbRating = imdbRating.orEmpty(),
        imdbVotes = imdbVotes.orEmpty(),
        imdbID = imdbID.orEmpty(),
        type = Type.orEmpty(),
        dvd = DVD.orEmpty(),
        boxOffice = BoxOffice.orEmpty(),
        production = Production.orEmpty(),
        website = Website.orEmpty(),
        response = Response.orEmpty()
    )
}