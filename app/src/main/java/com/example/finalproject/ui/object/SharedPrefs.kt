package com.example.finalproject.ui.`object`

import android.content.SharedPreferences

object SharedPrefs {

    private var prefs: SharedPreferences? = null

    fun sharedPrefs(sharedPreferences: SharedPreferences) {
        prefs = sharedPreferences
    }

    fun putString(key: String, value: String) {
        prefs?.edit()?.putString(key, value)?.apply()
    }


    fun putList(key: String, list: List<Int>) {
        prefs?.edit()?.putString(key, list.joinToString(separator = ","))?.apply()
    }

    fun getString(key: String): String? {
        return prefs?.getString(key, null)
    }

    fun getList(key: String){}
}