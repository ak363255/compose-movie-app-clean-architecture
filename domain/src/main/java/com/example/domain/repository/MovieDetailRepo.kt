package com.example.domain.repository

import com.example.domain.models.MovieDetail
import com.example.domain.models.Result
import com.example.domain.usecase.MovieDetailUseCase

interface MovieDetailRepo {
    suspend fun getMovieDetail(params:MovieDetailUseCase.MovieDetailUseCaseParams):Result<MovieDetail>
}