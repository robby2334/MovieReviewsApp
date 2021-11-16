package com.dev.divig.moviereviewsapp.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import com.dev.divig.moviereviewsapp.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MoviePreference @Inject constructor(@ApplicationContext context: Context) {
    private var preference: SharedPreferences = context.getSharedPreferences(NAME, MODE)

    companion object {
        private const val NAME = BuildConfig.APPLICATION_ID + "_preferences"
        private const val MODE = Context.MODE_PRIVATE
        private val PREF_IS_FIRST_RUN_APP = Pair("IS_FIRST_RUN_APP", true)
        private val PREF_AUTH_TOKEN = Pair("PREF_AUTH_TOKEN", null)
        private val PREF_LOGIN_SESSION = Pair("PREF_LOGIN_SESSION", true)
        private val PREF_LOGIN_USERNAME = Pair("PREF_LOGIN_USERNAME", null)
        private val PREF_LOGIN_EMAIL = Pair("PREF_LOGIN_EMAIL", null)
    }

    var isFirstRunApp: Boolean
        get() = preference.getBoolean(PREF_IS_FIRST_RUN_APP.first, PREF_IS_FIRST_RUN_APP.second)
        set(value) = preference.edit {
            it.putBoolean(PREF_IS_FIRST_RUN_APP.first, value)
        }

    var authToken: String?
        get() = preference.getString(PREF_AUTH_TOKEN.first, PREF_AUTH_TOKEN.second)
        set(value) = preference.edit {
            it.putString(PREF_AUTH_TOKEN.first, value)
        }

    var isLoginSession: Boolean
        get() = preference.getBoolean(PREF_LOGIN_SESSION.first, PREF_LOGIN_SESSION.second)
        set(value) = preference.edit {
            it.putBoolean(PREF_LOGIN_SESSION.first, value)
        }

    var loginUsername: String?
        get() = preference.getString(PREF_LOGIN_USERNAME.first, PREF_LOGIN_USERNAME.second)
        set(value) = preference.edit {
            it.putString(PREF_LOGIN_USERNAME.first, value)
        }

    var loginEmail: String?
        get() = preference.getString(PREF_LOGIN_EMAIL.first, PREF_LOGIN_EMAIL.second)
        set(value) = preference.edit {
            it.putString(PREF_LOGIN_EMAIL.first, value)
        }
}

private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = edit()
    operation(editor)
    editor.apply()
}