package com.example.movieapp.dimodule.viewmodelmodule

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.ui.composables.movieSearchScreen.MovieSearchViewModel
import com.example.movieapp.ui.composables.movielistscreen.viemodel.MovieListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewmodelModule = module {
       viewModelOf(::MovieListViewModel)
       viewModelOf(::MovieSearchViewModel)
   //  factory<MovieListViewModel> { MovieListViewModel(get()) }
}