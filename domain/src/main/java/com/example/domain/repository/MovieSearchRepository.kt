package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.models.Movie
import com.example.domain.models.Result
import com.example.domain.usecase.MovieSearchUseCase
import kotlinx.coroutines.flow.Flow

interface MovieSearchRepository {
    suspend fun searchMovie(params: MovieSearchUseCase.MovieSearchUseCaseParams): Flow<PagingData<Movie>>
}