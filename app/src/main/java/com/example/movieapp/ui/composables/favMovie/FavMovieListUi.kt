package com.example.movieapp.ui.composables.favMovie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.domain.models.MovieDetail
import com.example.movieapp.ui.composables.moviehomepageui.MovieMainRouter
import com.example.movieapp.ui.theme.MovieAppColor

@Composable
fun FavMovieListUi(
    modifier: Modifier = Modifier,
    mainRouter: MovieMainRouter,
    movies: List<MovieDetail>,
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(MovieAppColor.White)
    ) {
        val lazyGridState = rememberLazyGridState()
        LazyVerticalGrid(
            modifier = Modifier.padding(PaddingValues(horizontal = 8.dp)),
            columns = GridCells.Fixed(3),
            state = lazyGridState,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(movies.size) { index ->
                FavMovieItem(
                    modifier = Modifier,
                    movie = movies[index],
                    onMovieItemClicked = {
                        mainRouter.goToMovieDetails(it.imdbID)
                    }
                )
            }
        }

    }
}