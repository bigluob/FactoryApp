package com.camc.factory.utils

import android.content.Context

class SharedPrefsManager private constructor(private val context: Context) {
    private val preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    fun getString(key: String, defaultValue: String?): String? = preferences.getString(key, defaultValue)
    companion object {
        private const val PREFERENCES = "sPrefs"
        @Synchronized
        fun newInstance(context: Context) = SharedPrefsManager(context)

    }

    fun putBoolean(key: String, value: Boolean) = preferences.edit().putBoolean(key, value).apply()

    fun putInt(key: String, value: Int) = preferences.edit().putInt(key, value).apply()

    fun getBoolean(key: String, defValue: Boolean) = preferences.getBoolean(key, defValue)

    fun getInt(key: String, defValue: Int) = preferences.getInt(key, defValue)


    fun putString(key: String, value: String) = preferences.edit().putString(key, value).apply()


}
