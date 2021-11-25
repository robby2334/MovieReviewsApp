package com.dev.divig.moviereviewsapp.data.network.model.response.movie

import com.google.gson.annotations.SerializedName

data class BaseMovieResponse<D>(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: D,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("status_message")
    val statusMessage: String?
)
