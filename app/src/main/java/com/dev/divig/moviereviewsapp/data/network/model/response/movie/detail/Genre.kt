package com.dev.divig.moviereviewsapp.data.network.model.response.movie.detail


import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)