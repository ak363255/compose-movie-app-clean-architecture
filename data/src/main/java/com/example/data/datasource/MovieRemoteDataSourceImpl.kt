package com.example.data.datasource

import com.example.data.datamodels.MovieDetailData
import com.example.data.datamodels.MovieResults
import com.example.data.datamodels.NetworkResponse
import com.example.data.netowrk.NetworkService
import com.example.data.urls.UrlConstants
import com.example.data.urls.UrlConstants.MOVIE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URLEncoder

class MovieRemoteDataSourceImpl(
    private val networkService: NetworkService
) : MovieDataSource.RemoteDataSource {
    override suspend fun getMovies(page: Int): NetworkResponse<MovieResults> =
        networkService.makeGetRequest<MovieResults>(MOVIE_URL+"&page=${page}")

    override suspend fun getMovieById(id: String): NetworkResponse<MovieDetailData> =
        networkService.makeGetRequest(UrlConstants.MOVIE_BY_ID_URL + "&i=${id}")

    override suspend fun searchMovie(
        query: String,
        page: Int
    ): NetworkResponse<MovieResults> = networkService.makeGetRequest<MovieResults>(
        UrlConstants.MOVIE_QUERY_URL +
                "&s=${
                    withContext(Dispatchers.IO) {
                        URLEncoder.encode(query.trim(), "UTF-8")
                    }
                }" +
                "&page=${page}"
    )
}