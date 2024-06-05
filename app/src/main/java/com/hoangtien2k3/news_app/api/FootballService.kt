package com.hoangtien2k3.news_app.api

import com.hoangtien2k3.news_app.models.Football
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface FootballService {
    @Headers(
        "x-rapidapi-host: free-football-soccer-videos.p.rapidapi.com",
        "x-rapidapi-key: 4877c410b9mshe7fe9517ac14094p1cd13ejsn6a566299c797"
    )
    @GET("/football")
    fun getFootballData(): Call<List<Football>>
}