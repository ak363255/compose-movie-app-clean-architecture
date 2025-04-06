package com.example.movieapp.dimodule.usecasemodule

import com.example.data.datasource.MovieDataSource
import com.example.data.datasource.MovieLoacalDataSourceImpl
import com.example.data.datasource.MovieRemoteDataSourceImpl
import com.example.data.repository.MovieRepositoryImpl
import com.example.data.repository.MovieSearchRepositoryImpl
import com.example.domain.repository.MovieRepository
import com.example.domain.repository.MovieSearchRepository
import com.example.domain.usecase.GetMoviePageUseCase
import com.example.domain.usecase.GetMoviesUseCase
import com.example.domain.usecase.MovieSearchUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single<MovieDataSource.LocalDataSource> {
        MovieLoacalDataSourceImpl()
    }
    single<MovieDataSource.RemoteDataSource> {
        MovieRemoteDataSourceImpl(get())
    }
    single<MovieRepository> {
        MovieRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get()
        )
    }
    single {
        GetMoviesUseCase(
            movieRepository = get()
        )
    }

    single {
        GetMoviePageUseCase(
            movieRepository = get()
        )
    }
    single<MovieSearchRepository> {
        MovieSearchRepositoryImpl(get())
    }
    single {
        MovieSearchUseCase(get())
    }
}