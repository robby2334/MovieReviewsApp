package com.dev.divig.moviereviewsapp.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import com.dev.divig.moviereviewsapp.BuildConfig

class MoviePreference(context: Context) {
    private var preference: SharedPreferences = context.getSharedPreferences(NAME, MODE)

    companion object {
        private const val NAME = BuildConfig.APPLICATION_ID + "_preferences"
        private const val MODE = Context.MODE_PRIVATE
        private val PREF_IS_FIRST_RUN_APP = Pair("IS_FIRST_RUN_APP", true)
    }

    var isFirstRunApp: Boolean
        get() = preference.getBoolean(PREF_IS_FIRST_RUN_APP.first, PREF_IS_FIRST_RUN_APP.second)
        set(value) = preference.edit {
            it.putBoolean(PREF_IS_FIRST_RUN_APP.first, value)
        }

}

private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = edit()
    operation(editor)
    editor.apply()
}

private fun SharedPreferences.delete() {
    edit().clear().apply()
}