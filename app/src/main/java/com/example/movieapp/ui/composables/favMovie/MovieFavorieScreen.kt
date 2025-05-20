package com.example.movieapp.ui.composables.favMovie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoveToInbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.domain.models.MovieDetail
import com.example.domain.models.Result
import com.example.movieapp.ui.composables.favMovie.viewmodel.FavMovieScreenViewModel
import com.example.movieapp.ui.composables.loader.FullPageLoader
import com.example.movieapp.ui.composables.moviehomepageui.MovieMainRouter
import com.example.movieapp.ui.theme.MovieAppColor

@Composable
fun FavoriteMovieScreen(
    mainRouter: MovieMainRouter,
    favMovieScreenViewModel: FavMovieScreenViewModel
) {

    LaunchedEffect(Unit) {
        favMovieScreenViewModel.getFavMovies()
    }
    val favMoviesState = favMovieScreenViewModel.movies.collectAsState()
    when (val data = favMoviesState.value) {
        is Result.Error -> {
            NoFavMovieFound()
        }

        Result.Loading -> {
            FullPageLoader(loaderSize = 32.dp)
        }

        is Result.Success<List<MovieDetail>> -> {
            val movies = data.data
            if (movies.isNotEmpty()) {
                Screen(movies)
                //FavMovieListUi(mainRouter = mainRouter, movies = movies)
            } else {
                NoFavMovieFound()
            }
        }
    }
}

@Composable
fun NoFavMovieFound(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(color = MovieAppColor.White)
    ) {
        val (noDataFound, title, subtitle) = createRefs()
        Image(
            imageVector = Icons.Default.MoveToInbox,
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .constrainAs(noDataFound) {
                    top.linkTo(parent.top, margin = 56.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Text(
            text = "No Favorites Added",
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(noDataFound.bottom, margin = 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MovieAppColor.Black
            )
        )
        Text(
            textAlign = TextAlign.Center,
            text = "Save a favorite movie and it will show up here",
            modifier = Modifier
                .constrainAs(subtitle) {
                    top.linkTo(title.bottom, margin = 48.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = MovieAppColor.Black
            )
        )

    }
}