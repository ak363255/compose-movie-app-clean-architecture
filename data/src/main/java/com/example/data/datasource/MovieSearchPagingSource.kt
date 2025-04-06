package com.example.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.datamodels.MovieResults
import com.example.data.datamodels.NetworkResponse
import com.example.data.datamodels.toDoman
import com.example.domain.models.Movie
import com.example.domain.models.Result

class MovieSearchPagingSource(
    private val query: String,
    private val remoteDataSource: MovieDataSource.RemoteDataSource
) : PagingSource<Int, Movie>(){
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key?:1
        val response = remoteDataSource.searchMovie(page = page, query = query)
        return when(response){
            is NetworkResponse.Error -> {
                LoadResult.Error(throw Exception(response.error))
            }
            is NetworkResponse.Success<MovieResults> -> {
                val domainData = response.data.toDoman()
                LoadResult.Page(
                    data = domainData.movies,
                    prevKey = if(page ==1) null else page -1,
                    nextKey = if(page < domainData.totalResult) page+1 else null,
                )
            }
        }
    }
}