package com.hiaashuu.debloatzzz.ui.theme

import android.content.Context
import android.content.SharedPreferences

object DisclaimerPrefs {
    private const val PREFS_NAME = "debloatzzz_prefs"
    private const val KEY_DISCLAIMER_ACCEPTED = "disclaimer_accepted"

    fun hasAccepted(context: Context): Boolean {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_DISCLAIMER_ACCEPTED, false)
    }

    fun setAccepted(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_DISCLAIMER_ACCEPTED, true).apply()
    }
}