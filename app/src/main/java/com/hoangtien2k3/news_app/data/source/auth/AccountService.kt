package com.hoangtien2k3.news_app.data.source.auth

import com.hoangtien2k3.news_app.data.sharedpreferences.DataLocalManager
import com.hoangtien2k3.news_app.network.ApiResponse
import com.hoangtien2k3.news_app.network.AuthenticationResponse
import com.hoangtien2k3.news_app.network.IntrospectResponse
import com.hoangtien2k3.news_app.network.UserResponse
import com.hoangtien2k3.news_app.network.request.ChangePasswordRequest
import com.hoangtien2k3.news_app.network.request.IntrospectRequest
import com.hoangtien2k3.news_app.network.request.LoginRequest
import com.hoangtien2k3.news_app.network.request.SignupRequest
import com.hoangtien2k3.news_app.network.request.UpdateUserRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface AccountService {
    @POST("/api/auth/signin")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<ApiResponse<AuthenticationResponse>>

    @POST("/api/user/signup")
    fun signup(
        @Body signupRequest: SignupRequest
    ): Call<ApiResponse<UserResponse>>

    @POST("/api/auth/introspect")
    fun authenticate(
        @Body request: IntrospectRequest
    ): Call<ApiResponse<IntrospectResponse>>

    @GET("/api/user/my-info")
    fun myinfo(): Call<ApiResponse<UserResponse>>

    @DELETE("/api/auth/delete/{id}")
    fun delete(
        @Path("id") id: Long
    ): Call<String>

    @PUT("/api/auth/update/{id}")
    fun updateUser(
        @Path("id") id: Long, @Body update: UpdateUserRequest
    ): Call<ApiResponse<UserResponse>>

    @PUT("/api/auth/changePassword")
    fun changePassword(
        @Body request: ChangePasswordRequest?
    ): Call<ApiResponse<UserResponse>>
}