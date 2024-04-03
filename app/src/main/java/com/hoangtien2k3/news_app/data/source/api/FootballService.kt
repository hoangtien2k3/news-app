package com.hoangtien2k3.news_app.data.source.api

import com.hoangtien2k3.news_app.data.models.Football
import com.hoangtien2k3.news_app.network.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface FootballService {
//    @Headers(
//        "x-rapidapi-host: free-football-soccer-videos.p.rapidapi.com",
//        "x-rapidapi-key: 4877c410b9mshe7fe9517ac14094p1cd13ejsn6a566299c797"
//    )
    @GET("/api/football")
    fun getFootballData(): Call<ApiResponse<List<Football>>>
}