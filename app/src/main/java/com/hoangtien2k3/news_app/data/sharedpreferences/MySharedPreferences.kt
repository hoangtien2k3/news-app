package com.hoangtien2k3.news_app.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class MySharedPreferences(context: Context) {
    private val MY_SHARED_PREFERENCES = "MY_SHARED_PREFERENCES"

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
    fun saveUserInfo(id: Long, name: String, role: String) {
        sharedPreferences.edit().putLong("user_id", id).apply()
        sharedPreferences.edit().putString("user_name", name).apply()
        sharedPreferences.edit().putString("user_role", role).apply()
    }

    // lấy ra Id
    fun getUserId(): Long {
        return sharedPreferences.getLong("user_id", 1)
    }
    fun getUserName(): String? {
        return sharedPreferences.getString("user_name", "")
    }
    fun getUserRole(): String? {
        return sharedPreferences.getString("user_role", "USER")
    }

//    fun getUserInfo(user_id: String): UserInfo {
//        val sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)
//        val username = sharedPreferences.getString("username", "") ?: ""
//        val email = sharedPreferences.getString("email", "") ?: ""
//        val age = sharedPreferences.getInt("age", 0)
//        return UserInfo(username, email, age)
//    }


}