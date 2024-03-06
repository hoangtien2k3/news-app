package com.hoangtien2k3.news_app.data.source.api

import com.hoangtien2k3.news_app.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FootballClient {
    private const val BASE_URL = Constants.BASE_URL_Foolball
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: FootballService by lazy {
        retrofit.create(FootballService::class.java)
    }
}