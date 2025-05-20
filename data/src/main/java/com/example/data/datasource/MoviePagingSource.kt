package com.example.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.datamodels.MovieResults
import com.example.data.datamodels.NetworkResponse
import com.example.data.datamodels.toDoman
import com.example.domain.models.Movie

class MoviePagingSource(
    private val remoteDataSource: MovieDataSource.RemoteDataSource
) : PagingSource<Int, Movie>(){
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key?:1
       val data = remoteDataSource.getMovies(page)
        return when(data){
            is NetworkResponse.Error -> {
                LoadResult.Error(Exception(data.error))
            }
            is NetworkResponse.Success<MovieResults> -> {
                val domainModel = data.data.toDoman()
                LoadResult.Page(
                    prevKey = if(page ==1) null else page-1,
                    nextKey = if(page < domainModel.totalResult) page+1 else null,
                    data = domainModel.movies
                )
            }
        }
    }

}