package com.example.movieapp.ui.composables.movieSearchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.models.Movie
import com.example.domain.usecase.MovieSearchUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieSearchViewModel(
    private val movieSearchUseCase: MovieSearchUseCase
) : ViewModel(){
    val _movieSearchUiState : MutableStateFlow<MovieSearchUiState> = MutableStateFlow(MovieSearchUiState())
    val movieSearchUiState : StateFlow<MovieSearchUiState> get() = _movieSearchUiState
    private val searchQueryFlow = MutableStateFlow("")

    private var _movies: MutableStateFlow<PagingData<Movie>> = MutableStateFlow(PagingData.empty())
    val movies: Flow<PagingData<Movie>> get() = _movies
    val coroutineExceptionHandler = CoroutineExceptionHandler { context,throwable->

    }
    var isSubscribed = false
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    fun subscribeSearch() = viewModelScope.launch(coroutineExceptionHandler){
        isSubscribed = true
        searchQueryFlow
            .debounce(500)
            .onEach {
                _movieSearchUiState.value = if(it.isEmpty()) MovieSearchUiState() else MovieSearchUiState(isDefaultState = false, isLoading = true)
            }
            .filter { it.isNotEmpty() && it.length >=3 }
            .flatMapLatest {
                movieSearchUseCase(MovieSearchUseCase.MovieSearchUseCaseParams(it))
            }.cachedIn(viewModelScope)
            .collectLatest {
                _movies.value = it
            }
    }

    fun onSearchClicked(query: String) = viewModelScope.launch(coroutineExceptionHandler){
        searchQueryFlow.emit(query)
    }

    fun onLoadStateUpdate(loadState: CombinedLoadStates, itemCount: Int) = viewModelScope.launch{
           val showFullPageLoader = loadState.refresh is LoadState.Loading
           val isError = loadState.refresh is LoadState.Error
          _movieSearchUiState.update {
              it.copy(
                  noResultFound = itemCount <=0,
                  isLoading = showFullPageLoader,
                  errorMsg = if(isError)"No Result Found" else ""
              )

          }

    }


}