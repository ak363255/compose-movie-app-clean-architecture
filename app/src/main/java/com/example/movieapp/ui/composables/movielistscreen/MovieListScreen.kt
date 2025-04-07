package com.example.movieapp.ui.composables.movielistscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.domain.models.Movie
import com.example.movieapp.ui.composables.moviehomepageui.MovieMainRouter
import com.example.movieapp.ui.theme.MovieAppColor

@Composable
fun MovieListScreen(
    modifier: Modifier = Modifier,
    mainRouter: MovieMainRouter,
    movies: LazyPagingItems<Movie>
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
            items(movies.itemCount) { index ->
                if (movies[index] != null) {
                    MovieItem(
                        modifier = Modifier,
                        movie = movies.get(index)!!,
                        onMovieItemClicked = {
                            mainRouter.goToMovieDetails(it.movieId)
                        }
                      )
                }
            }

            if (movies.loadState.append is LoadState.Loading) {
                item(span = {
                    _root_ide_package_.androidx.compose.foundation.lazy.grid.GridItemSpan(
                        maxLineSpan
                    )
                }) {
                    Loader()
                }
            }
        }

    }
}

@Composable
fun Loader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


