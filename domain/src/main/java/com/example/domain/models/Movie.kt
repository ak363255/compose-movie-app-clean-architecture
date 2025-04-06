package com.example.domain.models


data class Movies(
    val movies : List<Movie>,
    val totalResult: Int,
)
data  class Movie(
    val movieName : String,
    val moviePoster : String,
    val movieId : String = "",
    val isFav: Boolean = false
)