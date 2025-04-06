package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.datamodels.MovieResults
import com.example.data.datamodels.NetworkResponse
import com.example.data.datamodels.toDoman
import com.example.data.datasource.MovieDataSource
import com.example.data.datasource.MoviePagingSource
import com.example.domain.models.Movie
import com.example.domain.models.Movies
import com.example.domain.models.Result
import com.example.domain.repository.MovieRepository
import com.example.domain.usecase.GetMoviesUseCase
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val localDataSource: MovieDataSource.LocalDataSource,
    private val remoteDataSource: MovieDataSource.RemoteDataSource,
) : MovieRepository {
    override suspend fun getMovies(params: GetMoviesUseCase.GetMovieUseCaseParam): Result<Movies> {
        val data = remoteDataSource.getMovies(params.pageNo)
        when (data) {
            is NetworkResponse.Error -> {
                return Result.Error(data.error)
            }
            is NetworkResponse.Success<MovieResults> -> {
                return Result.Success(data.data.toDoman())
            }
        }

    }
    override suspend fun getMoviePageData(params: GetMoviesUseCase.GetMovieUseCaseParam): Flow<PagingData<Movie>> {
        val moviePagingSource = MoviePagingSource(remoteDataSource)
        return Pager(
            PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                moviePagingSource
            }
        ).flow

    }

}