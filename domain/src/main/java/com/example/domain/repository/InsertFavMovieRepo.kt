package com.example.domain.repository

import com.example.domain.models.MovieDetail
import com.example.domain.models.Result

interface InsertFavMovieRepo {
    suspend fun insertMovie(movieDetail: MovieDetail):Result<String>
}