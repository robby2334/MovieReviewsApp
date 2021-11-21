package com.dev.divig.moviereviewsapp.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

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

    fun getDate(): String {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        val formatter = SimpleDateFormat(Constant.RELEASE_DATE_PATTERN, Locale.getDefault())
        return formatter.format(calendar.time)
    }

    fun dateToMillis(value: String?): Long {
        val date = value ?: "0000-00-00"
        val formatterPrev =
            SimpleDateFormat(Constant.RELEASE_DATE_PATTERN, Locale.getDefault())
        val originalDate = formatterPrev.parse(date) as Date
        return originalDate.time
    }

    fun hideSoftKeyboard(activity: Activity, view: View) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun dpToPixels(context: Context?, dp: Int): Int {
        if (context != null) {
            val scale = context.resources.displayMetrics.density
            return (dp * scale + 0.5f).toInt()
        }
        return 0
    }

    fun randomNumber(size: Int): ArrayList<Int> {
        val list = ArrayList<Int>(size)
        list.add(getRandomNumber())
        while (list.size < size) {
            val num = getRandomNumber()
            if (list.last() != num) list.add(num)
        }
        return list
    }

    private fun getRandomNumber() = (1..19).random()

    fun splitGenre(genre: String?): Array<String> {
        return genre.orEmpty().split(", ".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
    }

    fun getGenreName(genreId: Int): String {
        val genreMap: MutableMap<Int, String> = HashMap()
        genreMap[28] = "Action"
        genreMap[12] = "Adventure"
        genreMap[16] = "Animation"
        genreMap[35] = "Comedy"
        genreMap[80] = "Crime"
        genreMap[99] = "Documentary"
        genreMap[18] = "Drama"
        genreMap[10751] = "Family"
        genreMap[14] = "Fantasy"
        genreMap[36] = "History"
        genreMap[27] = "Horror"
        genreMap[10402] = "Music"
        genreMap[9648] = "Mystery"
        genreMap[10749] = "Romance"
        genreMap[878] = "Science"
        genreMap[10770] = "TV Movie"
        genreMap[53] = "Thriller"
        genreMap[10752] = "War"
        genreMap[37] = "Western"

        var result = ""
        genreMap.forEach {
            if (it.key == genreId) result = it.value
        }
        return result
    }

    fun Context.isInternetAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        @Suppress("DEPRECATION")
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                val cap = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
                return cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            }
            else -> {
                val networks = cm.allNetworks
                for (n in networks) {
                    val nInfo = cm.getNetworkInfo(n)
                    if (nInfo != null && nInfo.isConnected) return true
                }
            }
        }
        return false
    }
}