package com.example.data.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.roomdb.dbmodel.MovieEntity
import com.example.data.roomdb.dbmodel.RatingEntity
import com.example.data.roomdb.moviedao.MovieDao

@Database(entities = [MovieEntity::class, RatingEntity::class], version = 1)
abstract class MovieDb : RoomDatabase() {
    abstract fun getMovieDao():MovieDao
}