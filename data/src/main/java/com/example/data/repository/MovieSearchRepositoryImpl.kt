package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.datasource.MovieDataSource
import com.example.data.datasource.MoviePagingSource
import com.example.data.datasource.MovieSearchPagingSource
import com.example.domain.models.Movie
import com.example.domain.models.Result
import com.example.domain.repository.MovieSearchRepository
import com.example.domain.usecase.MovieSearchUseCase
import kotlinx.coroutines.flow.Flow

class MovieSearchRepositoryImpl(
    private val remoteDataSource: MovieDataSource.RemoteDataSource
) : MovieSearchRepository {
    override suspend fun searchMovie(params: MovieSearchUseCase.MovieSearchUseCaseParams): Flow<PagingData<Movie>> {
        val pagingSource = MovieSearchPagingSource(query = params.query,remoteDataSource)
        return Pager(

            config = PagingConfig(
                pageSize = 10

            ),
            pagingSourceFactory = {
                pagingSource
            },
        ).flow
    }
}