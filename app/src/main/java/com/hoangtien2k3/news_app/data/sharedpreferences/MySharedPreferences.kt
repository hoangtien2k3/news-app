package com.hoangtien2k3.news_app.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class MySharedPreferences(context: Context) {
    private val MY_SHARED_PREFERENCES = "MY_SHARED_PREFERENCES"

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun putBooleanValue(key: String, value: Boolean) {
        sharedPreferences.edit {
            this.clear(

            )
        }
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBooleanValue(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun removeValue(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}