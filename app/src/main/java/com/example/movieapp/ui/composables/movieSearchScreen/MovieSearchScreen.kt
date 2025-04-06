package com.example.movieapp.ui.composables.movieSearchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieapp.ui.composables.moviehomepageui.MovieMainRouter
import com.example.movieapp.ui.composables.movielistscreen.Loader
import com.example.movieapp.ui.composables.movielistscreen.MovieItem
import com.example.movieapp.ui.theme.MovieAppColor


@Composable
fun MovieSearchScreen(
    modifier: Modifier = Modifier,
    mainRouter: MovieMainRouter,
    movieSearchViewModel: MovieSearchViewModel
) {
    val searchUiState = movieSearchViewModel.movieSearchUiState.collectAsState()
    val moviesListState = movieSearchViewModel.movies.collectAsLazyPagingItems()
    LaunchedEffect(Unit) {
        if(!movieSearchViewModel.isSubscribed){
            movieSearchViewModel.subscribeSearch()
        }
    }
    movieSearchViewModel.onLoadStateUpdate(moviesListState.loadState,moviesListState.itemCount)
    Scaffold(
        topBar = {
            SearchTopBar(
                onQueryChanged = movieSearchViewModel::onSearchClicked,
                onBackClicked = {}
            )
        }
    ) { padding ->
        Box(modifier = modifier.padding(padding).padding(top = 12.dp)) {
            if(searchUiState.value.isDefaultState){
                Text("Default state")
            }else if(searchUiState.value.isLoading){
                CircularProgressIndicator()
            }else if(searchUiState.value.noResultFound){
                Text(searchUiState.value.errorMsg)
            }else{
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

        }
    }


}

@Composable
fun SearchTopBar(
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit,
    onBackClicked: () -> Unit
) {
    var query = remember {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
    }
    TextField(
        value = query.value,
        onValueChange = {
            query.value = it
            onQueryChanged(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        trailingIcon = {
            if (query.value.isNotEmpty()) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            query.value = ""
                            onQueryChanged("")
                        },
                    tint = MovieAppColor.Black
                )
            }
        },
        leadingIcon = {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onBackClicked()
                    },
                tint = MovieAppColor.Black
            )
        },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            keyboardController?.hide()
        })

    )
}