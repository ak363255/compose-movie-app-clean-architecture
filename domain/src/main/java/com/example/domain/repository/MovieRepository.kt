package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.models.Movie
import com.example.domain.models.Movies
import com.example.domain.models.Result
import com.example.domain.usecase.GetMoviesUseCase
import kotlinx.coroutines.flow.Flow

interface MovieRepository{
    suspend fun getMovies(
         params : GetMoviesUseCase.GetMovieUseCaseParam
    ) :  Result<Movies>

    suspend fun getMoviePageData(params: GetMoviesUseCase.GetMovieUseCaseParam): Flow<PagingData<Movie>>
}