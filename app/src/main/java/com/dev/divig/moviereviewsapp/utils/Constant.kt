package com.dev.divig.moviereviewsapp.utils

import com.dev.divig.moviereviewsapp.BuildConfig

object Constant {
    const val KEY_EXTRA_ID = "key_extra_id"
    const val KEY_EXTRA_IS_SEARCH = "key_extra_is_search"
    const val API_KEY = BuildConfig.api_key
    const val BASE_URL_IMAGE = BuildConfig.image_url
    const val VIDEOS = "videos"
    const val TRAILER = "Trailer"
    const val DATABASE_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val DATE_PATTERN = "d MMM yyyy HH:mm:ss"
    const val RELEASE_DATE_PATTERN = "yyyy-MM-dd"
    const val ONE_HOURS = 60
    const val ZERO_FLOAT = 0F
    const val FIVE = 5
    const val ITEM_TYPE_IMAGE_SLIDER = 1
    const val ITEM_TYPE_CHILD = 2
    const val NOW_PLAYING_MOVIES = "Now Playing Movies"
    const val ACTION_UPDATE = 101
    const val ACTION_GET = 102
    const val TYPE_CHANGE_PROFILE_DIALOG = "type_change_profile_dialog"
    const val TYPE_LOGOUT_DIALOG = "type_logout_dialog"
}