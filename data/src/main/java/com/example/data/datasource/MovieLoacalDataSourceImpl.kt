package com.example.data.datasource

import com.example.data.datasource.MovieDataSource
import com.example.domain.models.Movie
import com.example.domain.models.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieLoacalDataSourceImpl : MovieDataSource.LocalDataSource {
    override fun getMovies(): Flow<Result<List<Movie>>> = flow{
    }

    override fun getMovieById(id: String): Flow<Result<Movie>> = flow{
    }
}