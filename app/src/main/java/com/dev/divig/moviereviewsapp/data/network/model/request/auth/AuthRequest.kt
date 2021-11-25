package com.dev.divig.moviereviewsapp.data.network.model.request.auth

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("password")
    var password: String? = null
)