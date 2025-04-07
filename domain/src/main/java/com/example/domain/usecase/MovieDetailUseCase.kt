package com.example.domain.usecase

import com.example.domain.models.MovieDetail
import com.example.domain.models.Result
import com.example.domain.repository.MovieDetailRepo

class MovieDetailUseCase(
    private val movieDetailRepo: MovieDetailRepo
) {
    suspend operator fun invoke(params: MovieDetailUseCaseParams) : Result<MovieDetail> = movieDetailRepo.getMovieDetail(params)
    data class MovieDetailUseCaseParams(
        val movieId : String
    )
}