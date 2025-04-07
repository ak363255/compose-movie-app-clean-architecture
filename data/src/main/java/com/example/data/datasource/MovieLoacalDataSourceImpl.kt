package com.example.data.datasource

import com.example.data.roomdb.dbmodel.MovieWithRating
import com.example.data.roomdb.dbmodel.toMovieEntity
import com.example.data.roomdb.dbmodel.toRatingEntity
import com.example.data.roomdb.moviedao.MovieDao
import com.example.domain.models.MovieDetail

class MovieLoacalDataSourceImpl(
    private val movieDao: MovieDao
) : MovieDataSource.LocalDataSource {
    override suspend fun getMovies(): List<MovieWithRating> {
        return movieDao.getAllMovieWithRatings()
    }

    override suspend fun insertMovie(movieDetail: MovieDetail) {
        movieDao.insertMovieWithRating(movieDetail.toMovieEntity(),movieDetail.ratings.map { it.toRatingEntity(movieDetail.imdbID) })
    }

    override suspend fun getMovieById(id: String): MovieWithRating? {
        return movieDao.getMovieWithRatings(id)
    }

    override suspend fun deleteMoviebyId(id: String) {
        movieDao.deleteMovieById(id)
    }

}