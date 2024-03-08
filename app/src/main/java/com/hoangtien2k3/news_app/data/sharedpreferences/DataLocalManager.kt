package com.hoangtien2k3.news_app.data.sharedpreferences

import android.content.Context

class DataLocalManager private constructor(context: Context) {
    private val FIRST_SHARED_PREFERENCES = "FIRST_SHARED_PREFERENCES"
    private val mySharedPreferences: MySharedPreferences = MySharedPreferences(context)

    companion object {
        private lateinit var instance: DataLocalManager

        fun init(mContext: Context) {
            if (!::instance.isInitialized) {
                instance = DataLocalManager(mContext)
            }
        }

        fun getInstance(): DataLocalManager {
            if (!::instance.isInitialized) {
                throw UninitializedPropertyAccessException("DataLocalManager chưa được khởi tạo. Hãy gọi init(context) trước khi sử dụng.")
            }
            return instance
        }
    }

    fun setFirstInstalled(isFirst: Boolean) {
        mySharedPreferences.putBooleanValue(FIRST_SHARED_PREFERENCES, isFirst)
    }

    fun getFirstInstalled(): Boolean {
        return mySharedPreferences.getBooleanValue(FIRST_SHARED_PREFERENCES)
    }
}