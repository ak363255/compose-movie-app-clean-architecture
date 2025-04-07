package com.example.movieapp.ui.composables.movieDetail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.toRoute
import com.example.domain.models.MovieDetail
import com.example.domain.models.Result
import com.example.domain.usecase.DeleteFavMovieUseCase
import com.example.domain.usecase.GetFavMovieFromIdUseCase
import com.example.domain.usecase.InsertMovieUseCase
import com.example.domain.usecase.MovieDetailUseCase
import com.example.movieapp.route.MovieRoutes
import com.example.movieapp.ui.composables.movieDetail.MovieDetailUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

class MovieDetailViewModel(
    private val movieDetailUseCase: MovieDetailUseCase,
    private val insertMovieUseCase: InsertMovieUseCase,
    private val getFavMovieFromIdUseCase: GetFavMovieFromIdUseCase,
    private val deleMovieUseCase: DeleteFavMovieUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _movieDetail: MutableStateFlow<Result<MovieDetail>> =
        MutableStateFlow(Result.Loading)
    val movieDetail: StateFlow<Result<MovieDetail>> get() = _movieDetail

    private val _movieDetailUiState: MutableStateFlow<MovieDetailUiState> = MutableStateFlow(MovieDetailUiState())
    val movieDetailUiState : StateFlow<MovieDetailUiState> get() = _movieDetailUiState

    fun getMovieDetail() = viewModelScope.launch {
        val params = getParam()
        if (params.movieId.isNotEmpty()) {
            _movieDetail.value = movieDetailUseCase(params)
        } else {
            _movieDetail.value = Result.Error("Invalid Movie Id")
        }
    }

    fun initMovieDetailState() = viewModelScope.launch{
         getShortListedMovie(getId())
    }
    fun toggleShortlistMovie(movieDetail: MovieDetail) = viewModelScope.launch{
         if(_movieDetailUiState.value.isFavMovie){
             deleteMovie(movieDetail)
             _movieDetailUiState.update {
                 it.copy(isFavMovie = false)
             }
         }else{
             insertFavMovie(movieDetail)
             _movieDetailUiState.update {
                 it.copy(isFavMovie = true)
             }
         }
    }

    fun getShortListedMovie(id: String) = viewModelScope.launch {
            val favMovie = getFavMovieFromIdUseCase(id)
        when(favMovie){
            is Result.Error -> {}
            Result.Loading -> {}
            is Result.Success<MovieDetail> -> {
                _movieDetailUiState.update {
                    it.copy(isFavMovie = true)
                }
            }
        }
    }

    private suspend fun insertFavMovie(movieDetail: MovieDetail)  {
        insertMovieUseCase(movieDetail)
    }

    private suspend fun deleteMovie(movieDetail: MovieDetail) {
         deleMovieUseCase(movieDetail.imdbID)
    }




    private fun getParam(): MovieDetailUseCase.MovieDetailUseCaseParams {
        val id = getId()
        return MovieDetailUseCase.MovieDetailUseCaseParams(movieId = id)
    }

    private fun getId(): String {
        val id = savedStateHandle.toRoute<MovieRoutes.MovieDetailScreen>().id
        return id
    }


}