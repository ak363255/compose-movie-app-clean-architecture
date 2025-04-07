package com.example.movieapp.ui.composables.moviehomepageui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.route.MovieGraph
import com.example.movieapp.route.MovieRoutes
import com.example.movieapp.ui.composables.movieDetail.MovieDetailScreen
import com.example.movieapp.ui.composables.movieDetail.viewmodel.MovieDetailViewModel
import com.example.movieapp.ui.composables.movieSearchScreen.MovieSearchScreen
import com.example.movieapp.ui.composables.movieSearchScreen.viewmodel.MovieSearchViewModel
import com.example.movieapp.utils.navBackStackEntryExt.NavBackStackEntryExt.scopedViewModel
import com.example.movieapp.utils.navgraphbuilder.NavGraphBuilderExtension.composableHorizontalSlide
import org.koin.androidx.compose.koinViewModel


@Composable
fun MovieHomeScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val nestedNavController = rememberNavController()
    NavHost(
        modifier = modifier,
        startDestination = MovieRoutes.MovieNavScreen,
        navController = navController,
        route = MovieGraph.MovieRoot::class
    ) {
        Log.d("MOVIE","screen recreated lambda")
        composableHorizontalSlide<MovieRoutes.MovieNavScreen> {
            Log.d("MOVIE","screen recreated")
            MovieNavScreen(
                mainRouter = MovieMainRouter(navController),
                nestedNavController = nestedNavController
            ) {
                MovieNestedNavGraph(
                    mainNavController = navController,
                    nestedNavController = nestedNavController,
                    parentRoute = MovieGraph.MovieRoot::class
                )
            }
        }

        composableHorizontalSlide<MovieRoutes.MovieDetailScreen> {
            val movieDetailViewModel : MovieDetailViewModel = koinViewModel()
            MovieDetailScreen(modifier = Modifier,movieDetailViewModel, mainRouter = MovieMainRouter(mainNavController = navController))
        }
        composableHorizontalSlide<MovieRoutes.MovieSearchScreen> { backStackEntry ->
            val movieSearchViewModel : MovieSearchViewModel = koinViewModel()
            MovieSearchScreen(
                mainRouter = MovieMainRouter(navController),
                modifier = Modifier,
                movieSearchViewModel = movieSearchViewModel
            )
        }

    }
}