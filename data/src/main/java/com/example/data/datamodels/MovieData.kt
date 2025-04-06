package com.example.data.datamodels

import com.example.domain.models.Movie
import com.example.domain.models.Movies

data class MovieResults(
  val Search : List<MovieData>?,
    val totalResults: String?,
    val Response: String?
)
data class MovieData(
    val Title : String?,
    val Year : String?,
    val imdbID : String?,
    val Type : String?,
    val Poster : String?,
)

fun MovieResults.toDoman(): Movies{
   val movies = mutableListOf<Movie>()
   this.Search?.forEach { item ->
       movies.add(Movie(
           movieName = item.Title?:"",
           moviePoster = item.Poster?:"",
           movieId = item.imdbID?:""
       ))
   }
    var totalResults = 0;
    try {
        totalResults = this.totalResults?.toInt()?:0
    }catch (e: Exception){
        totalResults = movies.size
    }
    return Movies(movies = movies,totalResult = totalResults)
}


