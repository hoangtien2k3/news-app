package com.hoangtien2k3.news_app.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class MySharedPreferences(context: Context) {
    private val MY_SHARED_PREFERENCES = "MY_SHARED_PREFERENCES"
    private val USER_NAME = "USER_NAME"
    private val ID = "ID"
    private val EMAIL = "EMAIL"
    private val NAME = "NAME"
    private val USER_ROLES = "USER_ROLES"
    private val TOKEN_KEY = "TOKEN_KEY"

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    // để lưu thông tin khi người dùng đăng nhập app
    fun putBooleanValue(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBooleanValue(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }


    // dùng để lưu thông tin những bản tin đã đọc của người dùng
    fun addReadItem(id: String, item: Any) {
        val readItems = getReadItems(id).toMutableSet()
        val json = Gson().toJson(item)
        readItems.add(json)
        sharedPreferences.edit().putStringSet(id, readItems).apply()
    }

    private fun getReadItems(id: String): Set<String> {
        return sharedPreferences.getStringSet(id, setOf()) ?: setOf()
    }


    // dùng để lưu trữ thông tin người dùng khi đăng nhập vào app
    fun saveUserInfo(id: Long, name: String, username: String, email: String, role: String) {
        sharedPreferences.edit().putLong(ID, id).apply()
        sharedPreferences.edit().putString(NAME, name).apply()
        sharedPreferences.edit().putString(USER_NAME, username).apply()
        sharedPreferences.edit().putString(EMAIL, email).apply()
        sharedPreferences.edit().putString(USER_ROLES, role).apply()
    }
    fun saveTokenKey(token: String) {
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
    }
    fun removeValueFromSharedPreferences() {
        sharedPreferences.edit().remove(ID).apply()
        sharedPreferences.edit().remove(NAME).apply()
        sharedPreferences.edit().remove(USER_NAME).apply()
        sharedPreferences.edit().remove(EMAIL).apply()
        sharedPreferences.edit().remove(USER_ROLES).apply()
    }


    fun getUserId(): Long {
        return sharedPreferences.getLong(ID, 0)
    }
    fun getName(): String? {
        return sharedPreferences.getString(NAME, "")
    }
    fun getUserName(): String? {
        return sharedPreferences.getString(USER_NAME, "")
    }
    fun getEmail(): String? {
        return sharedPreferences.getString(EMAIL, "")
    }
    fun getUserRole(): String? {
        return sharedPreferences.getString(USER_ROLES, "USER")
    }
    fun getTokenKey(): String? {
        return sharedPreferences.getString(TOKEN_KEY, "")
    }

}