package com.dev.divig.moviereviewsapp.data.network.model.response.auth

import com.google.gson.annotations.SerializedName

data class BaseAuthResponse<D, E>(
    @SerializedName("success")
    val isSuccess: Boolean,
    @SerializedName("data")
    val data: D,
    @SerializedName("errors")
    val errorMsg: E,
)
