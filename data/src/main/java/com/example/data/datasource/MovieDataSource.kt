package com.example.data.datasource

import com.example.data.datamodels.MovieDetailData
import com.example.data.datamodels.MovieResults
import com.example.data.datamodels.NetworkResponse
import com.example.data.roomdb.dbmodel.MovieEntity
import com.example.data.roomdb.dbmodel.MovieWithRating
import com.example.domain.models.Movie
import com.example.domain.models.MovieDetail
import com.example.domain.models.Movies
import com.example.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface  MovieDataSource {
    interface LocalDataSource{
        suspend fun getMovieById(id: String): MovieWithRating?
        suspend fun deleteMoviebyId(id: String)
        suspend fun getMovies(): List<MovieWithRating>
        suspend fun  insertMovie(movieDetail: MovieDetail)
    }
    interface RemoteDataSource{
        suspend fun getMovies(page: Int): NetworkResponse<MovieResults>
        suspend fun getMovieById(id: String): NetworkResponse<MovieDetailData>
        suspend fun searchMovie(query: String,page:Int): NetworkResponse<MovieResults>

    }
}