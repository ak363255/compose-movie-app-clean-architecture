package com.example.domain.repository

interface DeleteFavMovieRepo {
    suspend fun deleteMovieById(id: String)
}