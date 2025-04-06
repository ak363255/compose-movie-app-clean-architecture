package com.example.domain.usecase

import com.example.domain.models.Movies
import com.example.domain.models.Result
import com.example.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(param: GetMovieUseCaseParam): Result<Movies> = movieRepository.getMovies(param)
    data class GetMovieUseCaseParam(
        val pageNo: Int,
    )
}