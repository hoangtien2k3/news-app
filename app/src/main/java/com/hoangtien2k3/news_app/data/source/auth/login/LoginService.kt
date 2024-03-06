package com.hoangtien2k3.news_app.data.source.auth.login

import com.hoangtien2k3.news_app.response.LoginRequest
import com.hoangtien2k3.news_app.data.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/api/auth/signin")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}