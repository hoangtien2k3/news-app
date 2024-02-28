package com.hoangtien2k3.news_app.api

import com.hoangtien2k3.news_app.response.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): Call<WeatherResponse>
}