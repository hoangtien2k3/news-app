package com.hoangtien2k3.news_app.data.sharedpreferences

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DataLocalManager.init(applicationContext)
    }
}