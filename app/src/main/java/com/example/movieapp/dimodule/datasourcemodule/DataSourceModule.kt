package com.example.movieapp.dimodule.datasourcemodule

import com.example.data.datasource.MovieDataSource
import com.example.data.datasource.MovieLoacalDataSourceImpl
import com.example.data.datasource.MovieRemoteDataSourceImpl
import org.koin.dsl.module

val datasourcemodule = module {
    single<MovieDataSource.LocalDataSource> {
        MovieLoacalDataSourceImpl(get())
    }
    single<MovieDataSource.RemoteDataSource> {
        MovieRemoteDataSourceImpl(get())
    }
}