package com.example.data.repository

import com.example.data.datasource.MovieDataSource
import com.example.data.datasource.MovieLoacalDataSourceImpl
import com.example.data.roomdb.dbmodel.toMovieEntity
import com.example.data.roomdb.dbmodel.toRatingEntity
import com.example.data.roomdb.moviedao.MovieDao
import com.example.domain.models.MovieDetail
import com.example.domain.models.Result
import com.example.domain.repository.InsertFavMovieRepo

class InsertMovieRepoImpl(
    private val localDataSource: MovieDataSource.LocalDataSource
) : InsertFavMovieRepo {
    override suspend fun insertMovie(movieDetail: MovieDetail): Result<String> {
        try {
            localDataSource.insertMovie(movieDetail)
            return Result.Success("Saved")
        } catch (e: Exception) {
            return Result.Error("Fail")
        }
    }

}