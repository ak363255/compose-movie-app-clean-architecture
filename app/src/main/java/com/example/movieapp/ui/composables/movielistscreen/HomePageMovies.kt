package com.example.movieapp.ui.composables.movielistscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieapp.ui.composables.moviehomepageui.MovieMainRouter
import com.example.movieapp.ui.composables.movielistscreen.viemodel.MovieListViewModel

@Composable
fun HomePageMovies(
    modifier: Modifier = Modifier,
    mainRouter: MovieMainRouter,
    movieListUiState: MovieListUiState,
    movieListViewModel: MovieListViewModel
) {
    val moviesListState = movieListViewModel.movies.collectAsLazyPagingItems()
    LaunchedEffect(Unit) {
        if (!movieListViewModel.isFetched) {
            movieListViewModel.getMovies(movieListViewModel.getMovieUseCaseParam(movieListViewModel.getPageNo() + 1))
        }
    }
    MovieListScreen(
        mainRouter = mainRouter,
        movies = moviesListState
    )
}