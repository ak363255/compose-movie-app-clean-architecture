package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.models.Movie
import com.example.domain.repository.MovieSearchRepository
import kotlinx.coroutines.flow.Flow

class MovieSearchUseCase(
    private val movieSearchRepository: MovieSearchRepository
) {

    suspend operator fun invoke(params: MovieSearchUseCaseParams): Flow<PagingData<Movie>> =
        movieSearchRepository.searchMovie(params)

    data class MovieSearchUseCaseParams(
        val query: String
    )
}