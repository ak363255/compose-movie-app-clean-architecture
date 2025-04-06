package com.example.movieapp.ui.composables.movieSearchScreen

data class MovieSearchUiState(
    val isDefaultState: Boolean = true,
    val noResultFound : Boolean = false,
    val isLoading : Boolean = false,
    val errorMsg: String = ""
)