package com.example.data.repository

import com.example.data.datamodels.MovieDetailData
import com.example.data.datamodels.NetworkResponse
import com.example.data.datamodels.toDomain
import com.example.data.datasource.MovieDataSource
import com.example.domain.models.MovieDetail
import com.example.domain.models.Result
import com.example.domain.repository.MovieDetailRepo
import com.example.domain.usecase.MovieDetailUseCase

class MovieDetailReposioryImpl(
    private val remoteDaaSource: MovieDataSource.RemoteDataSource
) : MovieDetailRepo {
    override suspend fun getMovieDetail(params: MovieDetailUseCase.MovieDetailUseCaseParams): Result<MovieDetail> {
        val response =  remoteDaaSource.getMovieById(params.movieId)
        return  when(response){
            is NetworkResponse.Error -> Result.Error("API Error")
            is NetworkResponse.Success<MovieDetailData> -> Result.Success(response.data.toDomain())
        }
    }
}