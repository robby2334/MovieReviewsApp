package com.dev.divig.moviereviewsapp.data.network.model.response.movie.detail


import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("videos")
    val videos: List<Video>?
)