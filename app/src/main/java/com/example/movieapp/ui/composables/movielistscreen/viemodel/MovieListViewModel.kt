package com.example.movieapp.ui.composables.movielistscreen.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.models.Movie
import com.example.domain.usecase.GetMoviePageUseCase
import com.example.domain.usecase.GetMoviesUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class MovieListViewModel(
    private val getMoviesUseCase: GetMoviePageUseCase
) : ViewModel() {


    private var _movies: MutableStateFlow<PagingData<Movie>> = MutableStateFlow(PagingData.empty())
    val movies: Flow<PagingData<Movie>> get() = _movies
    val coroutineExceptionHandler = CoroutineExceptionHandler { context,throwable->

    }
    var isFetched = false
    fun getMovies(param: GetMoviesUseCase.GetMovieUseCaseParam) = viewModelScope.launch(coroutineExceptionHandler){
        isFetched = true
        getMoviesUseCase(pageUseCaseParam = param)
            .cachedIn(viewModelScope)
            .onEach { pagingData ->
                _movies.value = pagingData
            }
            .launchIn(viewModelScope)
    }

    private var moviesUseCaseParams: GetMoviesUseCase.GetMovieUseCaseParam? = null
    private var pageNo: Int = 0
    fun getPageNo(): Int = pageNo
    fun getMovieUseCaseParam(pageNo: Int): GetMoviesUseCase.GetMovieUseCaseParam {
        this.pageNo = pageNo
        if (moviesUseCaseParams == null) {
            moviesUseCaseParams = GetMoviesUseCase.GetMovieUseCaseParam(pageNo = pageNo)
        } else {
            moviesUseCaseParams = moviesUseCaseParams!!.copy(pageNo = pageNo)
        }
        return moviesUseCaseParams!!
    }
}