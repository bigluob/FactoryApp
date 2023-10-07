package com.camc.common.utils

import android.content.Context
import android.content.SharedPreferences

object PreferenceUtils {
    private const val PREF_NAME = "app_preferences"

    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getString(context: Context, key: String, defaultValue: String): String {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun putString(context: Context, key: String, value: String) {
        val sharedPreferences = getSharedPreferences(context)
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getInt(context: Context, key: String, defaultValue: Int): Int {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun putInt(context: Context, key: String, value: Int) {
        val sharedPreferences = getSharedPreferences(context)
        sharedPreferences.edit().putInt(key, value).apply()
    }

    // 其他类型的获取和存储方法，如Boolean、Long等
}