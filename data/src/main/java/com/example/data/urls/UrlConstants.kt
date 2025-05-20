package com.example.data.urls

import com.example.data.BuildConfig

object UrlConstants {
    val API_KEY = BuildConfig.API_KEY
    const val BASE_URL = "https://www.omdbapi.com/"
    val MOVIE_URL = "${BASE_URL}?apikey=${API_KEY}&s=movie"
    val MOVIE_BY_ID_URL = "${BASE_URL}?apikey=${API_KEY}&plot=full"
    val MOVIE_QUERY_URL = "${BASE_URL}?apikey=${API_KEY}&type=movie"
}