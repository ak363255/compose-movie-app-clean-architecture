package com.example.movieapp.dimodule.repomodule

import com.example.data.repository.DeleteMovieByIdRepoImpl
import com.example.data.repository.GetFavMovieFromIdRepoImpl
import com.example.data.repository.GetFavMovieRepoImpl
import com.example.data.repository.InsertMovieRepoImpl
import com.example.data.repository.MovieDetailReposioryImpl
import com.example.data.repository.MovieRepositoryImpl
import com.example.data.repository.MovieSearchRepositoryImpl
import com.example.domain.repository.DeleteFavMovieRepo
import com.example.domain.repository.GetFavMovieFromIdRepo
import com.example.domain.repository.GetFavMovieRepo
import com.example.domain.repository.InsertFavMovieRepo
import com.example.domain.repository.MovieDetailRepo
import com.example.domain.repository.MovieRepository
import com.example.domain.repository.MovieSearchRepository
import org.koin.dsl.module

val repoModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    single<MovieSearchRepository> { MovieSearchRepositoryImpl(get()) }
    single<MovieDetailRepo> { MovieDetailReposioryImpl(get()) }
    single<InsertFavMovieRepo> { InsertMovieRepoImpl(get()) }
    single<GetFavMovieFromIdRepo> { GetFavMovieFromIdRepoImpl(get()) }
    single<DeleteFavMovieRepo> { DeleteMovieByIdRepoImpl(get()) }
    single<GetFavMovieRepo> { GetFavMovieRepoImpl(get()) }

}