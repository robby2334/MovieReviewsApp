package com.dev.divig.moviereviewsapp.utils

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun convertRuntime(runtime: Int): String {
        val hours = runtime / Constant.ONE_HOURS
        val minutes = runtime % Constant.ONE_HOURS
        return if (runtime >= Constant.ONE_HOURS) {
            "${hours}h ${minutes}m"
        } else {
            "${minutes}m"
        }
    }

    fun dateFormatter(value: String?): String? {
        val date = value ?: "0000-00-00T00:00:00.000Z"
        val formatterPrev =
            SimpleDateFormat(Constant.DATABASE_DATE_PATTERN, Locale.getDefault())
        val originalDate = formatterPrev.parse(date)

        val formatter = SimpleDateFormat(Constant.DATE_PATTERN, Locale.getDefault())
        return formatter.format(originalDate as Date)
    }
}