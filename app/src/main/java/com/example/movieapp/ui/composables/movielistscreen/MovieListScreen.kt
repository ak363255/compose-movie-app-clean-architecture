package com.example.movieapp.ui.composables.movielistscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.domain.models.Movie
import com.example.movieapp.ui.composables.moviehomepageui.MovieMainRouter
import com.example.movieapp.ui.composables.movielistscreen.viemodel.MovieListViewModel
import com.example.movieapp.ui.theme.MovieAppColor
import com.example.movieapp.utils.image.ImageUtil

@Composable
fun MovieListScreen(
    modifier: Modifier = Modifier,
    mainRouter: MovieMainRouter,
    movieListUiState: MovieListUiState,
    movieListViewModel: MovieListViewModel
) {
    val moviesListState = movieListViewModel.movies.collectAsLazyPagingItems()
    LaunchedEffect(Unit) {
        if(!movieListViewModel.isFetched){
            movieListViewModel.getMovies(movieListViewModel.getMovieUseCaseParam(movieListViewModel.getPageNo()+1))
        }
    }
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
            items(moviesListState.itemCount) { index ->
                if(moviesListState.get(index)!= null){
                    MovieItem(modifier = Modifier, movie = moviesListState.get(index)!!,onFavClicked = {movieClicked ->
                    })
                }
            }

            if(moviesListState.loadState.append is LoadState.Loading){
                item (span = {
                    _root_ide_package_.androidx.compose.foundation.lazy.grid.GridItemSpan(maxLineSpan)
                }){
                     Loader()
                }
            }
        }

    }
}

@Composable
fun Loader(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onFavClicked : (Movie)-> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp)),

        ) {
        val (image,fav) = createRefs()
        ImageUtil.loadImage(
            imageUrl = movie.moviePoster,
            modifier = Modifier
                .aspectRatio(9/16f)
                .constrainAs (image){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            loaderPlaceHolder = {},
            errorPlaceHolder = {}
        )
        Icon(Icons.Filled.Favorite, contentDescription = null,modifier = Modifier.constrainAs(fav) {
            top.linkTo(parent.top, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
        }.clickable{
            onFavClicked(movie)
        }.size(16.dp),
            tint = if(movie.isFav) MovieAppColor.RedC1 else MovieAppColor.White
        )
    }
}
