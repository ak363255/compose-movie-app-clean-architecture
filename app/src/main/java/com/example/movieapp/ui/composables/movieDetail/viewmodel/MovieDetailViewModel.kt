package com.example.movieapp.ui.composables.movieDetail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.toRoute
import com.example.domain.models.MovieDetail
import com.example.domain.models.Result
import com.example.domain.usecase.GetFavMovieFromIdUseCase
import com.example.domain.usecase.InsertMovieUseCase
import com.example.domain.usecase.MovieDetailUseCase
import com.example.movieapp.route.MovieRoutes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

class MovieDetailViewModel(
    private val movieDetailUseCase: MovieDetailUseCase,
    private val insertMovieUseCase: InsertMovieUseCase,
    private val getFavMovieFromIdUseCase: GetFavMovieFromIdUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _movieDetail: MutableStateFlow<Result<MovieDetail>> =
        MutableStateFlow(Result.Loading)
    val movieDetail: StateFlow<Result<MovieDetail>> get() = _movieDetail

    fun getMovieDetail() = viewModelScope.launch {
        val params = getParam()
        if (params.movieId.isNotEmpty()) {
            _movieDetail.value = movieDetailUseCase(params)
        } else {
            _movieDetail.value = Result.Error("Invalid Movie Id")
        }
    }


    private fun getParam(): MovieDetailUseCase.MovieDetailUseCaseParams {
        val id = getId()
        return MovieDetailUseCase.MovieDetailUseCaseParams(movieId = id)
    }
    private fun getId ():String{
        val id = savedStateHandle.toRoute<MovieRoutes.MovieDetailScreen>().id
        return id
    }

    private val _shortListState : MutableStateFlow<Result<MovieDetail>> = MutableStateFlow(Result.Loading)
    val shortListState : StateFlow<Result<MovieDetail>> get() = _shortListState
    fun getShortListedMovie(id:String) = viewModelScope.launch {
        _shortListState.value = getFavMovieFromIdUseCase(id)
    }
    fun getShortListedMovie()  {
        getShortListedMovie(getId())
    }
     fun insertFavMovie(movieDetail: MovieDetail) = viewModelScope.launch {
        insertMovieUseCase(movieDetail)
    }


}