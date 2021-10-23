package com.dev.divig.moviereviewsapp.data.local.room.dao

import androidx.room.*
import com.dev.divig.moviereviewsapp.data.model.MovieEntity
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>): Long

    @Query("SELECT * FROM tb_movie")
    suspend fun getMovies(): List<MovieEntity>

    @Query("SELECT * FROM tb_movie WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: ReviewEntity): Long

    @Delete
    suspend fun deleteReview(movie: MovieEntity): Int

    @Query("SELECT * FROM tb_review WHERE movie_id = :movieId")
    suspend fun getReviewsByMovieId(movieId: Int): List<ReviewEntity>
}