package com.example.movieapp.route

import kotlinx.serialization.Serializable

sealed class MovieRoutes {
    @Serializable
    data class MovieDetailScreen(val id: String) : MovieRoutes()

    @Serializable
    data object MovieSearchScreen : MovieRoutes()

    @Serializable
    data object MovieFavScreen : MovieRoutes()

    @Serializable
    data object MovieNavScreen : MovieRoutes()

    @Serializable
    data object MovieListScreen : MovieRoutes()

}

fun MovieRoutes.route() = this.javaClass.canonicalName

sealed class MovieGraph {
    @Serializable
    data object MovieRoot : MovieGraph()

}