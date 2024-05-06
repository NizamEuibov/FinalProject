package com.example.finalproject.ui.`object`

import android.content.SharedPreferences

object SharedPrefs {

    private var prefs: SharedPreferences? = null

    fun sharedPrefs(sharedPreferences: SharedPreferences) {
        prefs = sharedPreferences
    }

    fun putUserId(key: String, value: Int) {
        prefs?.edit()?.putInt(key, value)?.apply()
    }

    fun getUserId(key: String): Int? {
        return prefs?.getInt(key, 0)
    }

    fun removeUserId(key: String) {
        prefs?.edit()?.remove(key)?.apply()
    }
}