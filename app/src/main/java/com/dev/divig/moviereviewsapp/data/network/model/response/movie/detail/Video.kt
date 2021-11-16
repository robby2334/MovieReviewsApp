package com.dev.divig.moviereviewsapp.data.network.model.response.movie.detail


import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("id")
    val id: String?,
    @SerializedName("key")
    val key: String?,
    @SerializedName("site")
    val site: String?
)