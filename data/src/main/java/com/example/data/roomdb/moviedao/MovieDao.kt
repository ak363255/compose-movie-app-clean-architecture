package com.example.data.roomdb.moviedao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.roomdb.dbmodel.MovieEntity
import com.example.data.roomdb.dbmodel.MovieWithRating
import com.example.data.roomdb.dbmodel.RatingEntity

@Dao
interface MovieDao {
    @Transaction
    @Query("SELECT * from movies where imdbID = :id")
    suspend fun getMovieWithRatings(id:String): MovieWithRating?

    @Transaction
    @Query("SELECT * from movies")
    suspend fun getAllMovieWithRatings():List<MovieWithRating>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieEntity(entity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRatingEntity(entity: RatingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRatingEntity(entity: List<RatingEntity>)
}