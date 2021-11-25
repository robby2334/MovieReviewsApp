package com.dev.divig.moviereviewsapp.data.network.model.response.auth

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("email")
    val email: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("username")
    val username: String?
)