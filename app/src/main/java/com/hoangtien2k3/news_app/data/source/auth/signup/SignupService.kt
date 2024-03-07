package com.hoangtien2k3.news_app.data.source.auth.signup

import com.hoangtien2k3.news_app.network.request.SignupRequest
import com.hoangtien2k3.news_app.network.response.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupService {
    @POST("/api/auth/signup")
    fun signup(@Body signupRequest: SignupRequest): Call<SignupResponse>
}