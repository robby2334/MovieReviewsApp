package com.dev.divig.moviereviewsapp.utils

import android.os.Build
import android.text.Html
import android.text.TextUtils
import android.util.Patterns
import android.widget.TextView

object StringUtils {
    fun isEmailValid(input: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(input)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(input as CharSequence).matches()
        }
    }

    fun TextView.textFromHtml(htmlString: String?) {
        text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlString, Html.FROM_HTML_MODE_COMPACT)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(htmlString)
        }
    }
}