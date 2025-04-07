package com.example.domain.usecase

import com.example.domain.models.MovieDetail
import com.example.domain.models.Result
import com.example.domain.repository.GetFavMovieFromIdRepo

class GetFavMovieFromIdUseCase(
    private val getFavMovieFromIdRepo: GetFavMovieFromIdRepo
) {
    suspend operator fun invoke(id:String) :Result<MovieDetail> = getFavMovieFromIdRepo.getFavMovieFromId(id)
}