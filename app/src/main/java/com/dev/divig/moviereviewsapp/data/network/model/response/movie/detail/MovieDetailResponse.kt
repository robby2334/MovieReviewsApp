package com.dev.divig.moviereviewsapp.data.network.model.response.movie.detail

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("videos")
    val videos: Videos?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("status_message")
    val statusMessage: String?

)