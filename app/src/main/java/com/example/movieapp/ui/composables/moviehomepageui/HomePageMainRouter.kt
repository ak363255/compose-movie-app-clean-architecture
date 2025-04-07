package com.example.movieapp.ui.composables.moviehomepageui

import androidx.navigation.NavController
import com.example.movieapp.route.MovieRoutes

class MovieMainRouter(private val mainNavController: NavController) {
    fun goToMovieDetails(movieId: String) {
        mainNavController.navigate(MovieRoutes.MovieDetailScreen(movieId))
    }
    fun goToSearchPage()  {
        mainNavController.navigate(MovieRoutes.MovieSearchScreen)
    }

    fun navigateUp(){
        mainNavController.navigateUp()
    }

}







