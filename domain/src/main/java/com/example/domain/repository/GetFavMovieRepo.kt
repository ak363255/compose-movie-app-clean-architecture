package com.example.domain.repository

import com.example.domain.models.MovieDetail
import com.example.domain.models.Result

interface GetFavMovieRepo {
    suspend fun getFavMovies(): Result<List<MovieDetail>>
}