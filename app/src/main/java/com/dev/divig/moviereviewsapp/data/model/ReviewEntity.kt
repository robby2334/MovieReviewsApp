package com.dev.divig.moviereviewsapp.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_review")
data class ReviewEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "movie_id")
    var movieId: Int,

    @ColumnInfo(name = "author")
    var author: String?,

    @ColumnInfo(name = "content")
    var content: String?,

    @ColumnInfo(name = "create_at")
    var createAt: String?,
)