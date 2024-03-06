package com.hoangtien2k3.news_app.data.source.api

import com.hoangtien2k3.news_app.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BanTinDemo {
    private const val BASE_URL = "http://192.168.1.254:9090"
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: BanTinService by lazy {
        retrofit.create(BanTinService::class.java)
    }
}