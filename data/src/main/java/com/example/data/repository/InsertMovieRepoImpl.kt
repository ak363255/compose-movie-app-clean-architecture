package com.example.data.repository

import com.example.data.roomdb.dbmodel.toRatingEntity
import com.example.data.roomdb.moviedao.MovieDao
import com.example.domain.models.MovieDetail
import com.example.domain.models.Result
import com.example.domain.repository.InsertFavMovieRepo

class InsertMovieRepoImpl(
    private val movieDao: MovieDao
) : InsertFavMovieRepo {
    override suspend fun insertMovie(movieDetail: MovieDetail): Result<String> {
        try {
            movieDao.insertAllRatingEntity(movieDetail.ratings.map { it.toRatingEntity(movieDetail.imdbID) })
            return Result.Success("Saved")
        } catch (e: Exception) {
            return Result.Error("Fail")
        }
    }

}