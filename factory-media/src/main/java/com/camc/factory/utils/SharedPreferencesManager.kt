package com.camc.factory.utils

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit

class SharedPreferencesManager(private val context: Context) {
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveToken(token: String) {
        sharedPreferences.edit {
            putString(KEY_TOKEN, token)
            apply()
        }
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    companion object {
        private const val KEY_TOKEN = "token_key"
    }

}
