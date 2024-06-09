package com.hoangtien2k3.news_app.data.source.api

import com.hoangtien2k3.news_app.data.models.BanTin
import com.hoangtien2k3.news_app.data.models.Football
import com.hoangtien2k3.news_app.data.models.NewsResponse
import com.hoangtien2k3.news_app.network.ApiResponse
import com.hoangtien2k3.news_app.network.AuthenticationResponse
import com.hoangtien2k3.news_app.network.IntrospectResponse
import com.hoangtien2k3.news_app.network.UserResponse
import com.hoangtien2k3.news_app.network.request.ChangePasswordRequest
import com.hoangtien2k3.news_app.network.request.IntrospectRequest
import com.hoangtien2k3.news_app.network.request.LoginRequest
import com.hoangtien2k3.news_app.network.request.PostNewsLetterRequest
import com.hoangtien2k3.news_app.network.request.SavePosRequest
import com.hoangtien2k3.news_app.network.request.SignupRequest
import com.hoangtien2k3.news_app.network.request.UpdateUserRequest
import com.hoangtien2k3.news_app.network.response.PostNewsLetterResponse
import com.hoangtien2k3.news_app.network.response.SavePosResponse
import com.hoangtien2k3.news_app.network.response.WeatherResponse
import com.hoangtien2k3.news_app.utils.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AppNewsService {
    /**
     * bản tin
     * **/
    @GET("/api/news/category/{category}")
    fun getBanTin(
        @Path("category") category: String?
    ): Call<ApiResponse<List<BanTin>>>

    @GET("/api/news/full")
    fun getFullBanTin(): Call<ApiResponse<List<BanTin>>>


    /**
     * bóng đá:
     * **/
//        @Headers(
//        "x-rapidapi-host: free-football-soccer-videos.p.rapidapi.com",
//        "x-rapidapi-key: 4877c410b9mshe7fe9517ac14094p1cd13ejsn6a566299c797"
//    )
    @GET("/api/football")
    fun getFootballData(): Call<ApiResponse<List<Football>>>


    /**
     * Thời tiết
     * **/
    @GET("weather")
    fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): Call<WeatherResponse>


    /**
     * Tài khoản
     * **/
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


    @Headers(
        "Authorizationt: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJob2FuZ3RpZW4yazMuY29tIiwic3ViIjoiYWRtaW4iLCJleHAiOjE3MTYwMTQ1MjgsImlhdCI6MTcxMzQyMjUyOCwic2NvcGUiOiJBRE1JTiJ9.47Cz77JJuod1aSLA2bHYL2TwFSuUxVcJg5XbmmJBagT1ntHMkftpLpL84LFTkI3XTBece7urIjFGmlxmJgKiKg"
    )
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


    /**
     *
     * NewsAPI
     * **/
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode: String = "in",
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): Response<NewsResponse>


    /**
     * PostNews
     * **/
    @POST("/api/news")
    fun postNews(
        @Body postNewsLetterRequest: PostNewsLetterRequest
    ): Call<PostNewsLetterResponse>


    /**
     * Save News
     * **/
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