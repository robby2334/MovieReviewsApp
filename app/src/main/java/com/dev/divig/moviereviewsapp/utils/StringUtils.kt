package com.dev.divig.moviereviewsapp.utils

import android.text.TextUtils
import android.util.Patterns

object StringUtils {
    fun isEmailValid(input: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(input)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(input as CharSequence).matches()
        }
    }
}