package com.dev.divig.moviereviewsapp.data.network.model.response.movie


import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: String
)