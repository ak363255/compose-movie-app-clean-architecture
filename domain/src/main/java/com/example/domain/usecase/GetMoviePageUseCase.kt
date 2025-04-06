package com.example.domain.usecase

import com.example.domain.repository.MovieRepository

class GetMoviePageUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(pageUseCaseParam: GetMoviesUseCase.GetMovieUseCaseParam) = movieRepository.getMoviePageData(params = pageUseCaseParam)
}