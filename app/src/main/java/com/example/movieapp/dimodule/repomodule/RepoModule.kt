package com.example.movieapp.dimodule.repomodule

import com.example.data.repository.GetFavMovieFromIdRepoImpl
import com.example.data.repository.InsertMovieRepoImpl
import com.example.data.repository.MovieDetailReposioryImpl
import com.example.data.repository.MovieRepositoryImpl
import com.example.data.repository.MovieSearchRepositoryImpl
import com.example.domain.repository.GetFavMovieFromIdRepo
import com.example.domain.repository.InsertFavMovieRepo
import com.example.domain.repository.MovieDetailRepo
import com.example.domain.repository.MovieRepository
import com.example.domain.repository.MovieSearchRepository
import org.koin.dsl.module

val repoModule = module {
    single<MovieRepository> {
        MovieRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get()
        )
    }
    single<MovieSearchRepository> {
        MovieSearchRepositoryImpl(get())
    }

    single<MovieDetailRepo> {
        MovieDetailReposioryImpl(
            remoteDaaSource = get()
        )
    }

    single<InsertFavMovieRepo> {
        InsertMovieRepoImpl(get())
    }
    single<GetFavMovieFromIdRepo> {
        GetFavMovieFromIdRepoImpl(get())
    }

}