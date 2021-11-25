package com.dev.divig.moviereviewsapp.data.network.model.response.movie.detail


import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("results")
    val results: List<Video>?
)