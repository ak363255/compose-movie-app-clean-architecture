package com.example.movieapp.dimodule.dbmodule

import androidx.room.Room
import com.example.data.roomdb.MovieDb
import com.example.data.roomdb.moviedao.MovieDao
import org.koin.dsl.module

val dbModule = module {

    single {
           Room.databaseBuilder(
               get(),
               MovieDb::class.java,
               "movie_db",
           ).fallbackToDestructiveMigration()
               .build()
    }

    single<MovieDao> {
        get<MovieDb>().getMovieDao()
    }

}