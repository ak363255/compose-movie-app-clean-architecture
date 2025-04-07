package com.example.data.repository

import com.example.data.datasource.MovieDataSource
import com.example.domain.repository.DeleteFavMovieRepo

class DeleteMovieByIdRepoImpl(
    private val localDataSource: MovieDataSource.LocalDataSource
): DeleteFavMovieRepo {
    override suspend fun deleteMovieById(id: String) {
           localDataSource.deleteMoviebyId(id)
    }
}