package com.example.movieapp.ui.composables.movieSearchScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieapp.ui.composables.loader.FullPageLoader
import com.example.movieapp.ui.composables.movieSearchScreen.viewmodel.MovieSearchViewModel
import com.example.movieapp.ui.composables.moviehomepageui.MovieMainRouter
import com.example.movieapp.ui.composables.movielistscreen.MovieListScreen


@Composable
fun MovieSearchScreen(
    modifier: Modifier = Modifier,
    mainRouter: MovieMainRouter,
    movieSearchViewModel: MovieSearchViewModel
) {
    val searchUiState = movieSearchViewModel.movieSearchUiState.collectAsState()
    val moviesListState = movieSearchViewModel.movies.collectAsLazyPagingItems()
    LaunchedEffect(Unit) {
        if (!movieSearchViewModel.isSubscribed) {
            movieSearchViewModel.subscribeSearch()
        }
    }
    movieSearchViewModel.onLoadStateUpdate(moviesListState.loadState, moviesListState.itemCount)
    Scaffold(
        topBar = {
            SearchTopBar(
                onQueryChanged = movieSearchViewModel::onSearchClicked,
                onBackClicked = {
                    mainRouter.navigateUp()
                }
            )
        }
    ) { padding ->
        Box(
            modifier = modifier
                .padding(padding)
                .padding(top = 12.dp)
        ) {
            if (searchUiState.value.isDefaultState) {
                MovieSearchDefaultScreen()
            } else if (searchUiState.value.isLoading) {
                FullPageLoader(loaderSize = 54.dp)
            } else if (searchUiState.value.noResultFound) {
                SearchResultNotFound()
            } else {
                MovieListScreen(mainRouter = mainRouter, movies = moviesListState)
            }

        }
    }
}