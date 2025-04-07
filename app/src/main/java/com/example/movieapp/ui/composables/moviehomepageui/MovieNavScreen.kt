package com.example.movieapp.ui.composables.moviehomepageui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieapp.route.route
import com.example.movieapp.ui.composables.bottomnav.BottomNavigations
import com.example.movieapp.ui.composables.bottomnav.BottomNavigations.bottomNavList
import com.example.movieapp.ui.theme.MovieAppColor

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