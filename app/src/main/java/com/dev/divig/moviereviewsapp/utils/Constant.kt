package com.dev.divig.moviereviewsapp.utils

import com.dev.divig.moviereviewsapp.BuildConfig

object Constant {
    const val KEY_EXTRA_ID = "key_extra_id"
    const val KEY_EXTRA_IS_SEARCH = "key_extra_is_search"
    const val API_KEY = BuildConfig.api_key
    const val BASE_URL_IMAGE = BuildConfig.image_url
    const val VIDEOS = "videos"
    const val TRAILER = "Trailer"
    const val DATE_PATTERN_DATABASE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val DATE_PATTERN = "d MMM yyyy HH:mm:ss"
    const val DATE_PATTERN_RELEASE = "yyyy-MM-dd"
    const val ONE_HOURS = 60
    const val ZERO_FLOAT = 0F
    const val FOURTEEN_FLOAT = 14F
    const val FIVE = 5
    const val TEEN = 10
    const val ITEM_TYPE_IMAGE_SLIDER = 1
    const val ITEM_TYPE_CHILD = 2
    const val ITEM_TYPE_LOGIN = 3
    const val ITEM_TYPE_REGISTER = 4
    const val NOW_PLAYING_MOVIES = "Now Playing Movies"
    const val TYPE_CHANGE_PROFILE_DIALOG = "type_change_profile_dialog"
    const val TYPE_ABOUT_DIALOG = "type_about"
    const val TYPE_LOGOUT_DIALOG = "type_logout_dialog"
    const val TYPE_PLACEHOLDER = 101
    const val TYPE_EMPTY = 102
    const val TYPE_LOST_CONNECTION = 103
    const val ACTION_LOGIN = 10
    const val ACTION_REGISTER = 11
}