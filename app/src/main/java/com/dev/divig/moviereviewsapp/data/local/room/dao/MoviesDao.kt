package com.dev.divig.moviereviewsapp.data.local.room.dao

import androidx.room.*
import com.dev.divig.moviereviewsapp.data.local.model.MovieEntity
import com.dev.divig.moviereviewsapp.data.local.model.ReviewEntity

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity): Long

    @Update
    suspend fun updateMovie(movie: MovieEntity): Int

    @Query("SELECT * FROM tb_movie")
    suspend fun getMovies(): List<MovieEntity>

    @Query("SELECT * FROM tb_movie WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity

    @Query("SELECT * FROM tb_movie WHERE isFavorite = 1")
    suspend fun getFavMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(reviews: List<ReviewEntity>): List<Long>

    @Query("SELECT * FROM tb_review WHERE movie_id = :movieId ORDER BY create_at DESC")
    suspend fun getReviewsByMovieId(movieId: Int): List<ReviewEntity>
}