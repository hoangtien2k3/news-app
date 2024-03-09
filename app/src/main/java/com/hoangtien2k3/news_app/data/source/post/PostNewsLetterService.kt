package com.hoangtien2k3.news_app.data.source.post

import com.hoangtien2k3.news_app.network.request.PostNewsLetterRequest
import com.hoangtien2k3.news_app.network.response.PostNewsLetterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PostNewsLetterService {
    @POST("/api/news")
    fun postNews(@Body postNewsLetterRequest: PostNewsLetterRequest): Call<PostNewsLetterResponse>
}