package com.example.data.datasource

import com.example.data.datamodels.MovieDetailData
import com.example.data.datamodels.MovieResults
import com.example.data.datamodels.NetworkResponse
import com.example.domain.models.Movie
import com.example.domain.models.Movies
import com.example.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface  MovieDataSource {
    interface LocalDataSource{
        fun getMovies(): Flow<Result<List<Movie>>>
        fun getMovieById(id: String): Flow<Result<Movie>>
    }
    interface RemoteDataSource{
        suspend fun getMovies(page: Int): NetworkResponse<MovieResults>
        suspend fun getMovieById(id: String): NetworkResponse<MovieDetailData>
        suspend fun searchMovie(query: String,page:Int): NetworkResponse<MovieResults>

    }
}