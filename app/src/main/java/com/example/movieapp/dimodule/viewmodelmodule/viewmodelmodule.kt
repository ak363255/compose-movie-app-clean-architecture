package com.example.movieapp.dimodule.viewmodelmodule

import com.example.movieapp.ui.composables.movieSearchScreen.viewmodel.MovieSearchViewModel
import com.example.movieapp.ui.composables.movielistscreen.viemodel.MovieListViewModel
import com.example.movieapp.ui.composables.movieDetail.viewmodel.MovieDetailViewModel

import org.koin.core.module.dsl.viewModelOf

import org.koin.dsl.module

val viewmodelModule = module {
       viewModelOf(::MovieListViewModel)
       viewModelOf(::MovieSearchViewModel)
       viewModelOf(::MovieDetailViewModel)
   //  factory<MovieListViewModel> { MovieListViewModel(get()) }
}