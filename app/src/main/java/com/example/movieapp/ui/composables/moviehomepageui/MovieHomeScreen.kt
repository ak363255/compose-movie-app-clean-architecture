package com.example.movieapp.ui.composables.moviehomepageui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.ui.composables.bottomnav.BottomNavigations
import com.example.movieapp.ui.composables.bottomnav.BottomNavigations.bottomNavList
import com.example.movieapp.ui.composables.movieSearchScreen.MovieSearchScreen
import com.example.movieapp.ui.composables.movieSearchScreen.MovieSearchViewModel
import com.example.movieapp.ui.composables.movielistscreen.MovieListScreen
import com.example.movieapp.ui.composables.movielistscreen.MovieListUiState
import com.example.movieapp.ui.composables.movielistscreen.viemodel.MovieListViewModel
import com.example.movieapp.ui.theme.MovieAppColor
import com.example.movieapp.utils.navBackStackEntryExt.NavBackStackEntryExt.scopedViewModel
import com.example.movieapp.utils.navgraphbuilder.NavGraphBuilderExtension.composableHorizontalSlide
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import kotlin.reflect.KClass

sealed class MovieRoutes {
    @Serializable
    data class MovieDetailScreen(val id: Int) : MovieRoutes()

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

class MovieMainRouter(private val mainNavController: NavController) {
    fun goToMovieDetails(movieId: Int) {
        mainNavController.navigate(MovieRoutes.MovieDetailScreen(movieId))
    }
    fun goToSearchPage()  {
        mainNavController.navigate(MovieRoutes.MovieSearchScreen)
    }

}

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
            Box(modifier = Modifier.fillMaxSize()) {
                Text("Detail Screen")
            }
        }
        composableHorizontalSlide<MovieRoutes.MovieSearchScreen> {backStackEntry ->
            val movieSearchViewModel : MovieSearchViewModel = backStackEntry.scopedViewModel(nestedNavController)
            MovieSearchScreen(
                 mainRouter = MovieMainRouter(navController),
                modifier = Modifier,
                movieSearchViewModel = movieSearchViewModel
            )
        }

    }
}


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

        composableHorizontalSlide<MovieRoutes.MovieListScreen> {backstackEntry ->
            Log.d("MOVIE","screen recreated inside")
            val movieListViewModel: MovieListViewModel = backstackEntry.scopedViewModel(nestedNavController)
            Box(modifier = Modifier.fillMaxSize()) {
                MovieListScreen(
                    movieListUiState = MovieListUiState(movieList = MovieListUiState.getDummyMovieList()),
                    mainRouter = MovieMainRouter(mainNavController),
                    movieListViewModel = movieListViewModel
                )
            }
        }
        composableHorizontalSlide<MovieRoutes.MovieFavScreen> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text("Favorite Screen")
            }
        }


    }
}


@Composable
fun MovieNavScreen(
    mainRouter: MovieMainRouter,
    nestedNavController: NavController,
    content: @Composable () -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .background(color = MovieAppColor.White),
        topBar = {
            MovieToolBar(modifier = Modifier, onSearchClicked = {
                mainRouter.goToSearchPage()
            })
        },
        bottomBar = {
            BottomNavigations.MovieHomeBottomNavigation(
                modifier = Modifier
                    .background(color = MovieAppColor.White)
                    .padding(8.dp),
                navController = nestedNavController,
                bottomNavList = bottomNavList,
                bottomNavItemClicked = { bottomItem ->
                    val currentRoute  = nestedNavController.currentDestination?.route
                    val clickedPageRoute = bottomItem.page
                    val samePage = currentRoute == clickedPageRoute.route()
                    Log.d("SAME","isSame ${samePage} ${currentRoute}  clicked ${clickedPageRoute.route()}")
                    if(!samePage){
                        nestedNavController.navigate(clickedPageRoute)
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                content()
            }
        }
    )

}


@Composable
fun MovieToolBar(modifier: Modifier = Modifier, onSearchClicked: () -> Unit) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MovieAppColor.White)
    ) {
        val (title, search, mode) = createRefs()
        Text(
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                start.linkTo(parent.start, margin = 16.dp)
                bottom.linkTo(parent.bottom)
            }, text = "Movie", style = TextStyle(
                fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = MovieAppColor.Black
            )
        )
        Icon(
            modifier = Modifier
                .constrainAs(search) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(mode.start, margin = 16.dp)

                }
                .size(24.dp)
                .clickable {
                    onSearchClicked()
                },
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = MovieAppColor.Black,

            )
        Icon(
            modifier = Modifier
                .constrainAs(mode) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, margin = 16.dp)

                }
                .size(24.dp),
            imageVector = Icons.Outlined.DarkMode,
            contentDescription = null,
            tint = MovieAppColor.Black,
        )
    }
}