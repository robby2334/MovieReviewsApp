package com.dev.divig.moviereviewsapp.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import com.dev.divig.moviereviewsapp.R
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

    fun getDate(): String {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        val formatter = SimpleDateFormat(Constant.DATABASE_DATE_PATTERN, Locale.getDefault())
        return formatter.format(calendar.time)
    }

    fun dateFormatter(value: String?): String? {
        val date = value ?: "0000-00-00T00:00:00.000Z"
        val formatterPrev =
            SimpleDateFormat(Constant.DATABASE_DATE_PATTERN, Locale.getDefault())
        val originalDate = formatterPrev.parse(date)

        val formatter = SimpleDateFormat(Constant.DATE_PATTERN, Locale.getDefault())
        return formatter.format(originalDate as Date)
    }

    fun hideSoftKeyboard(activity: Activity, view: View) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showAlertDialog(
        context: Context?,
        title: String?,
        message: String?,
        onClickPositiveButton: (Boolean) -> Unit
    ) {
        context?.let {
            val alertDialogBuilder = AlertDialog.Builder(it)
            alertDialogBuilder.setTitle(title.orEmpty())
            alertDialogBuilder
                .setMessage(message.orEmpty())
                .setCancelable(false)
                .setPositiveButton(it.getString(R.string.text_title_ok)) { _, _ ->
                    onClickPositiveButton(true)
                }
                .setNegativeButton(it.getString(R.string.text_title_cancel)) { dialog, _ ->
                    onClickPositiveButton(false)
                    dialog.cancel()
                }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }
}