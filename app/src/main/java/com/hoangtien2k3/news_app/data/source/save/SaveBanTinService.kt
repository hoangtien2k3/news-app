package com.hoangtien2k3.news_app.data.source.save

import com.hoangtien2k3.news_app.network.request.SavePosRequest
import com.hoangtien2k3.news_app.network.response.SavePosResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SaveBanTinService {
    @POST("/api/post")
    fun postNewsSave(
        @Body savePost: SavePosRequest
    ): Call<SavePosResponse>

    @GET("/api/post")
    fun getAllNewsSave(): Call<List<SavePosResponse>>

    @GET("/api/post/all/{userId}")
    fun getAllNewsSaveByUserId(
        @Path("userId") userId: Long
    ): Call<List<SavePosResponse>>

    @DELETE("/api/post/all")
    fun deleteAllPostNews(): Call<Void>

}