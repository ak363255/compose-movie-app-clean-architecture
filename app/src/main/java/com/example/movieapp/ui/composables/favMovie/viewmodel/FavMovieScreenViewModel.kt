package com.example.movieapp.ui.composables.favMovie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.MovieDetail
import com.example.domain.models.Result
import com.example.domain.usecase.GetFavMovieUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavMovieScreenViewModel(
    private val getFavMovieUseCase: GetFavMovieUseCase
): ViewModel() {

    private val _movies : MutableStateFlow<Result<List<MovieDetail>>> = MutableStateFlow(Result.Loading)
    val movies : StateFlow<Result<List<MovieDetail>>> get() = _movies

    val coroutineExceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { context,throwable -> }
    fun getFavMovies() = viewModelScope.launch(coroutineExceptionHandler) {
        _movies.value = getFavMovieUseCase()
    }

}