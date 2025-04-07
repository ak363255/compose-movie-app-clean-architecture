package com.example.movieapp.ui.composables.moviehomepageui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.movieapp.route.MovieRoutes
import com.example.movieapp.ui.composables.favMovie.FavoriteMovieScreen
import com.example.movieapp.ui.composables.favMovie.viewmodel.FavMovieScreenViewModel
import com.example.movieapp.ui.composables.movielistscreen.HomePageMovies
import com.example.movieapp.ui.composables.movielistscreen.MovieListUiState
import com.example.movieapp.ui.composables.movielistscreen.viemodel.MovieListViewModel
import com.example.movieapp.utils.navBackStackEntryExt.NavBackStackEntryExt.scopedViewModel
import com.example.movieapp.utils.navgraphbuilder.NavGraphBuilderExtension.composableHorizontalSlide
import org.koin.androidx.compose.koinViewModel
import kotlin.reflect.KClass


@Composable
fun MovieNestedNavGraph(
    mainNavController: NavHostController,
    nestedNavController: NavHostController,
    parentRoute: KClass<*>
) {
    NavHost(
        navController = nestedNavController,
        startDestination = MovieRoutes.MovieListScreen,
        route = parentRoute
    ) {

        composableHorizontalSlide<MovieRoutes.MovieListScreen> { backstackEntry ->
            Log.d("MOVIE", "screen recreated inside")
            val movieListViewModel: MovieListViewModel =
                backstackEntry.scopedViewModel(nestedNavController)
            Box(modifier = Modifier.fillMaxSize()) {
                HomePageMovies(
                    movieListUiState = MovieListUiState(movieList = MovieListUiState.getDummyMovieList()),
                    mainRouter = MovieMainRouter(mainNavController),
                    movieListViewModel = movieListViewModel
                )
            }
        }
        composableHorizontalSlide<MovieRoutes.MovieFavScreen> {
            val favMovieScreenViewModel: FavMovieScreenViewModel = koinViewModel()
            FavoriteMovieScreen(
                mainRouter = MovieMainRouter(mainNavController),
                favMovieScreenViewModel = favMovieScreenViewModel
            )
        }


    }
}