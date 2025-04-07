package com.example.data.repository

import com.example.data.datasource.MovieDataSource
import com.example.data.roomdb.dbmodel.toMovieDetail
import com.example.domain.models.MovieDetail
import com.example.domain.models.Result
import com.example.domain.repository.GetFavMovieRepo

class GetFavMovieRepoImpl(
    private val localDataSource: MovieDataSource.LocalDataSource
) : GetFavMovieRepo{
    override suspend fun getFavMovies(): Result<List<MovieDetail>> {
        val movies = localDataSource.getMovies().map { it.toMovieDetail() }
        return Result.Success(movies)
    }
}