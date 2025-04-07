package com.example.domain.usecase

import com.example.domain.models.MovieDetail
import com.example.domain.repository.InsertFavMovieRepo

class InsertMovieUseCase(
    private val insertFavMovieRepo: InsertFavMovieRepo
) {
    suspend operator fun invoke(movieDetail: MovieDetail) = insertFavMovieRepo.insertMovie(movieDetail)
}