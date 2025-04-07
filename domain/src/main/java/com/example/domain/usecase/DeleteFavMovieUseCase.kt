package com.example.domain.usecase

import com.example.domain.repository.DeleteFavMovieRepo

class DeleteFavMovieUseCase(
    private val deleteFavMovieRepo: DeleteFavMovieRepo
) {
    suspend operator fun invoke(movieId: String) = deleteFavMovieRepo.deleteMovieById(movieId)
}