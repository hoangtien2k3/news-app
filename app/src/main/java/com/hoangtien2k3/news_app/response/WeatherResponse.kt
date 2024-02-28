package com.hoangtien2k3.news_app.response

data class WeatherResponse(
    val name: String,
    val dt: Long,
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val clouds: Clouds,
    val sys: Sys
)

data class Weather(
    val main: String,
    val icon: String
)

data class Main(
    val temp: Double,
    val humidity: Int
)

data class Wind(
    val speed: Double
)

data class Clouds(
    val all: Int
)

data class Sys(
    val country: String
)