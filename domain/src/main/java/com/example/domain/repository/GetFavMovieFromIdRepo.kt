package com.example.domain.repository

import com.example.domain.models.MovieDetail
import com.example.domain.models.Result

interface GetFavMovieFromIdRepo {
    suspend fun getFavMovieFromId(id:String):Result<MovieDetail>
}