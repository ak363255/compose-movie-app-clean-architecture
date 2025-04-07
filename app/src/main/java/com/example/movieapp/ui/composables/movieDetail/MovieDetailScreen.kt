package com.example.movieapp.ui.composables.movieDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.PlaylistAddCheck
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.domain.models.MovieDetail
import com.example.domain.models.Result
import com.example.movieapp.ui.composables.loader.FullPageLoader
import com.example.movieapp.ui.composables.movieDetail.viewmodel.MovieDetailViewModel
import com.example.movieapp.ui.composables.moviehomepageui.MovieMainRouter
import com.example.movieapp.ui.theme.MovieAppColor
import com.example.movieapp.utils.image.ImageUtil

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    movieDetailViewModel: MovieDetailViewModel,
    mainRouter: MovieMainRouter
) {
    LaunchedEffect(Unit) {
        movieDetailViewModel.getMovieDetail()
        movieDetailViewModel.getShortListedMovie()
    }
    val movieDetail = movieDetailViewModel.movieDetail.collectAsState()
    val shortListState = movieDetailViewModel.shortListState.collectAsState()
    when (val state = movieDetail.value) {
        is Result.Error -> {
            MovieNotFoundPage()
        }

        Result.Loading -> {
            FullPageLoader(loaderSize = 56.dp)
        }

        is Result.Success<MovieDetail> -> {
            MovieDetailUi(
                movieDetail = state.data, modifier = modifier, onBackArrowClicked = {
                mainRouter.navigateUp()
            }, onShortListIconClicked = { shortListedMovie ->
                movieDetailViewModel.insertFavMovie(shortListedMovie)
            },
                isFav = shortListState.value is Result.Success
            )
        }
    }
}

@Composable
fun MovieNotFoundPage(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(color = MovieAppColor.White)
    ) {
        val (noDataFound, title, subtitle) = createRefs()
        Image(
            imageVector = Icons.AutoMirrored.Filled.PlaylistAddCheck,
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
            text = "Movie Not Found",
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
            text = "Something went wrong!!.",
            modifier = Modifier
                .constrainAs(subtitle) {
                    top.linkTo(title.bottom, margin = 32.dp)
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

@Composable
fun MovieDetailTopBar(onBackArrowClicked: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = MovieAppColor.White)
    ) {
        val (backArrow, overview) = createRefs()
        Icon(
            Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .constrainAs(backArrow) {
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(overview.top)
                    bottom.linkTo(overview.bottom)
                }
                .clickable {
                    onBackArrowClicked()
                },
            tint = MovieAppColor.Black

        )
        Text(
            text = "Overview",
            modifier = Modifier
                .constrainAs(overview) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(backArrow.end, margin = 16.dp)
                },
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MovieAppColor.Black
            )
        )
    }
}

@Composable
fun MovieDetailUi(
    movieDetail: MovieDetail,
    modifier: Modifier = Modifier,
    onBackArrowClicked: () -> Unit,
    isFav: Boolean = false,
    onShortListIconClicked: (MovieDetail) -> Unit
) {
    Scaffold(
        topBar = {
            MovieDetailTopBar {
                onBackArrowClicked()
            }
        }
    ) {
        ConstraintLayout(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
                .background(MovieAppColor.White)
                .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical)
        ) {
            val (image, movieName, desc, shortListIcon) = createRefs()
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .constrainAs(shortListIcon) {
                        end.linkTo(parent.end, margin = 32.dp)
                        bottom.linkTo(parent.bottom, margin = 32.dp)
                    }
                    .clickable {
                        onShortListIconClicked(movieDetail)
                    },
                tint = if (isFav) MovieAppColor.RedC1 else MovieAppColor.GrayB3
            )
            ImageUtil.loadImage(
                imageUrl = movieDetail.poster,
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f),
                loaderPlaceHolder = {

                },
                errorPlaceHolder = {

                }
            )
            Text(
                text = movieDetail.title,
                modifier = Modifier
                    .constrainAs(movieName) {
                        top.linkTo(image.bottom, margin = 12.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    },
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MovieAppColor.Black
                )
            )
            Text(
                text = movieDetail.plot,
                modifier = Modifier
                    .constrainAs(desc) {
                        top.linkTo(movieName.bottom, margin = 12.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        width = Dimension.fillToConstraints

                    },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = MovieAppColor.Black
                )
            )

        }
    }


}