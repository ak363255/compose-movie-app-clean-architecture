package com.example.domain.usecase

import com.example.domain.repository.GetFavMovieRepo

class GetFavMovieUseCase (
    private val getFavMovieRepo: GetFavMovieRepo
){
    suspend operator fun invoke() = getFavMovieRepo.getFavMovies()
}