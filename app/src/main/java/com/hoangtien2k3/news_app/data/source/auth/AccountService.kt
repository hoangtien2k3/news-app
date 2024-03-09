package com.hoangtien2k3.news_app.data.source.auth

import com.hoangtien2k3.news_app.network.request.ChangePasswordRequest
import com.hoangtien2k3.news_app.network.request.LoginRequest
import com.hoangtien2k3.news_app.network.request.SignupRequest
import com.hoangtien2k3.news_app.network.request.UpdateUserRequest
import com.hoangtien2k3.news_app.network.response.ChangePasswordResponse
import com.hoangtien2k3.news_app.network.response.LoginResponse
import com.hoangtien2k3.news_app.network.response.SignupResponse
import com.hoangtien2k3.news_app.network.response.UpdateUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountService {
    @POST("/api/auth/signin")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/api/auth/signup")
    fun signup(@Body signupRequest: SignupRequest): Call<SignupResponse>

    @DELETE("/api/auth/delete/{id}")
    fun delete(@Path("id") id: Long): Call<String>

    @PUT("/api/auth/update/{id}")
    fun updateUser(@Path("id") id: Long, @Body update: UpdateUserRequest): Call<UpdateUserResponse>

    @PUT("/api/auth/changePassword")
    fun changePassword(@Body request: ChangePasswordRequest?): Call<ChangePasswordResponse>
}