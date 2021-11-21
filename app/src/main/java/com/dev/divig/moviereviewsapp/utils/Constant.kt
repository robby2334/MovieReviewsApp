package com.dev.divig.moviereviewsapp.utils

import com.dev.divig.moviereviewsapp.BuildConfig

object Constant {
    const val KEY_EXTRA_ID = "key_extra_id"
    const val API_KEY = BuildConfig.api_key
    const val BASE_URL_IMAGE = BuildConfig.image_url
    const val VIDEOS = "videos"
    const val DATABASE_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val DATE_PATTERN = "d MMM yyyy HH:mm:ss"
    const val ONE_HOURS = 60
    const val ONE_SECOND = 1000L
    const val THREE_SECOND = 3000L
    const val ZERO_FLOAT = 0F
    const val ACTION_UPDATE = 101
    const val ACTION_GET = 102
    const val TYPE_CHANGE_PROFILE_DIALOG = "tag_change_profile_dialog"
    const val TYPE_LOGOUT_DIALOG = "tag_logout_dialog"
}