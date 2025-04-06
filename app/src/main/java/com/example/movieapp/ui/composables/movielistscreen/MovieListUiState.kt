package com.example.movieapp.ui.composables.movielistscreen

import com.example.domain.models.Movie


data class MovieListUiState(
    val movieList : List<Movie>
){
    companion object{
        fun getDummyMovieList(): List<Movie>{
            val movieList = mutableListOf<Movie>()
            movieList.add(Movie(movieName = "Iron Man", moviePoster = "https://images.wallpapersden.com/image/download/marvel-iron-man-art_a21mZ2eUmZqaraWkpJRnZmtlrWhtaWU.jpg"))
            movieList.add(Movie(movieName = "Iron Man", moviePoster = "https://images.wallpapersden.com/image/download/marvel-iron-man-art_a21mZ2eUmZqaraWkpJRnZmtlrWhtaWU.jpg"))
            movieList.add(Movie(movieName = "Iron Man", moviePoster = "https://images.wallpapersden.com/image/download/marvel-iron-man-art_a21mZ2eUmZqaraWkpJRnZmtlrWhtaWU.jpg"))
            movieList.add(Movie(movieName = "Iron Man", moviePoster = "https://images.wallpapersden.com/image/download/marvel-iron-man-art_a21mZ2eUmZqaraWkpJRnZmtlrWhtaWU.jpg"))
            movieList.add(Movie(movieName = "Iron Man", moviePoster = "https://images.wallpapersden.com/image/download/marvel-iron-man-art_a21mZ2eUmZqaraWkpJRnZmtlrWhtaWU.jpg"))
            movieList.add(Movie(movieName = "Iron Man", moviePoster = "https://images.wallpapersden.com/image/download/marvel-iron-man-art_a21mZ2eUmZqaraWkpJRnZmtlrWhtaWU.jpg"))
            movieList.add(Movie(movieName = "Iron Man", moviePoster = "https://images.wallpapersden.com/image/download/marvel-iron-man-art_a21mZ2eUmZqaraWkpJRnZmtlrWhtaWU.jpg"))
            movieList.add(Movie(movieName = "Iron Man", moviePoster = "https://images.wallpapersden.com/image/download/marvel-iron-man-art_a21mZ2eUmZqaraWkpJRnZmtlrWhtaWU.jpg"))
            movieList.add(Movie(movieName = "Iron Man", moviePoster = "https://images.wallpapersden.com/image/download/marvel-iron-man-art_a21mZ2eUmZqaraWkpJRnZmtlrWhtaWU.jpg"))
            movieList.add(Movie(movieName = "Iron Man", moviePoster = "https://images.wallpapersden.com/image/download/marvel-iron-man-art_a21mZ2eUmZqaraWkpJRnZmtlrWhtaWU.jpg"))
            movieList.add(Movie(movieName = "Iron Man", moviePoster = "https://images.wallpapersden.com/image/download/marvel-iron-man-art_a21mZ2eUmZqaraWkpJRnZmtlrWhtaWU.jpg"))
            movieList.add(Movie(movieName = "Iron Man", moviePoster = "https://images.wallpapersden.com/image/download/marvel-iron-man-art_a21mZ2eUmZqaraWkpJRnZmtlrWhtaWU.jpg"))
            movieList.add(Movie(movieName = "Iron Man", moviePoster = "https://images.wallpapersden.com/image/download/marvel-iron-man-art_a21mZ2eUmZqaraWkpJRnZmtlrWhtaWU.jpg"))
            return movieList
        }
    }
}