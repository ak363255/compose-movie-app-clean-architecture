package com.example.data.repository

import com.example.data.datasource.MovieDataSource
import com.example.data.roomdb.MovieDb
import com.example.data.roomdb.dbmodel.toMovieDetail
import com.example.data.roomdb.moviedao.MovieDao
import com.example.domain.models.MovieDetail
import com.example.domain.models.Result
import com.example.domain.repository.GetFavMovieFromIdRepo

class GetFavMovieFromIdRepoImpl (
    private val  localDataSource: MovieDataSource.LocalDataSource
): GetFavMovieFromIdRepo {
    override suspend fun getFavMovieFromId(id: String): Result<MovieDetail> {
        val movieDetail = localDataSource.getMovieById(id)?.toMovieDetail()
       return if(movieDetail != null){
            Result.Success(movieDetail)
        }else{
           Result.Error("No Movie Found")
       }
    }
}